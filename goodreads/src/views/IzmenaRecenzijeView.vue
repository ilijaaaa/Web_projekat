<template>
    <h2 class="subheading">Izmena recenzije</h2>
    <form @submit.prevent="recenzija" class="form">
        <div class="form-group">
            <div class="star-rating-container">
                <vue-star-rating :show-rating="false" :rating=recenzijaDto.ocena @update:rating="setRating"></vue-star-rating>
            </div>
        </div>
        <div class="form-group">
            <textarea v-model="recenzijaDto.tekst">{{recenzijaDto.tekst}}</textarea>
        </div>
        <button type="submit" class="submit-button">Izmeni</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
</template>

<script>
import axios from 'axios';
import VueStarRating from 'vue-star-rating';

export default {
    components: {
        VueStarRating,
    },
    data() {
        return {
            recenzijaDto: {
                ocena: '',
                tekst: '',
                datum: new Date().toISOString().substring(0, 10),
            },
            loading: true,
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            axios.get('http://localhost:8080/api/recenzija/' + this.$route.query.id)
                .then(response => {
                    console.log(response.data);
                    this.recenzijaDto = response.data;
                    this.loading = false;
                })
                .catch(error => {
                    console.error(error);
                    alert('Recenzija ne postoji')
                    this.loading = false;
                });
        },
        setRating(rating) {
            this.recenzijaDto.ocena = rating;
        },
        recenzija() {
            axios
                .put('http://localhost:8080/api/recenzija/' + this.$route.query.id + '/knjiga/' + this.$route.query.knjiga_id, this.recenzijaDto, { params: { sessionId: localStorage.getItem('korisnik') } })
                .then(response => {
                    console.log(response.data);
                    this.$router.go(-1);
                })
                .catch(error => {
                    console.error(error);
                    if (error.response && error.response.data)
                        this.errorMessage = error.response.data;
                });
        }
    }
};
</script>

<style scoped>
.star-rating-container {
    display: flex;
    justify-content: center;
}

.subheading {
    color: #007bff;
    font-size: 20px;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 20px;
}

textarea {
    width: 25%;
    height: 150px;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    resize: vertical;
}

textarea:focus {
    outline: none;
    border-color: #007bff;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.submit-button {
    width: 8%;
    padding: 10px;
    font-size: 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-left: 12px;
}

.submit-button:hover {
    background-color: darkblue;
}

.error {
    color: red;
    margin-top: 10px;
}
</style>
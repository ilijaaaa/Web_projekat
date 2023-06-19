<template>
    <h2 class="subheading">Dodaj knjigu</h2>
    <form @submit.prevent="dodajKnjigu" class="form">
        <div class="form-group">
            <input type="text" v-model="knjigaDto.naslov" placeholder="Naslov" required/><br />
        </div>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.slika" placeholder="Slika" required/><br />
        </div>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.opis" placeholder="Opis" required/><br />
        </div>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.isbn" placeholder="ISBN" required/><br />
        </div>
        <div class="form-group">
            <input type="date" v-model="knjigaDto.datum" placeholder="Datum izdavanja" required/><br />
        </div>
        <div class="form-group">
            <input type="number" v-model="knjigaDto.brStr" placeholder="Broj strana" required/><br />
        </div>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.zanr" placeholder="Zanr" required/><br />
        </div>
        <div class="form-group">
            <input type="number" v-model="knjigaDto.autor" placeholder="ID autora"  required/><br />
        </div>
        <button type="submit" class="submit-button">Dodaj</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            knjigaDto: {
                naslov: '',
                slika: '',
                opis: '',
                isbn: '',
                datum: null,
                brStr: '',
                zanr: '',
                autor: '',
            },
            errorMessage: '',
        };
    },
    methods: {
        dodajKnjigu() {
            axios
                .post('http://localhost:8080/api/knjiga', this.knjigaDto, { params: { sessionId: localStorage.getItem('korisnik') } })
                .then(response => {
                    console.log(response.data);
                    this.$router.push("/profil");
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
.heading {
    font-size: 38px;
    margin-bottom: 10px;
}

.form-group {
    margin-bottom: 20px;
}

input[type="text"],
input[type="date"],
input[type="number"] {
    width: 25%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

textarea {
    width: 25%;
    height: 100px;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    resize: vertical;
}

.submit-button {
    width: 7%;
    padding: 10px;
    font-size: 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-button:hover {
    background-color: #0056b3;
}

.error {
    font-size: 20px;
    color: #f00;
    margin-top: 20px;
}
</style>
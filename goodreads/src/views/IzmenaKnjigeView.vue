<template>
    <h2 class="subheading">Izmeni knjigu</h2>
    <form @submit.prevent="izmeniKnjigu" class="form">
        <label>Naslov:</label>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.naslov" /><br />
        </div>
        <label>Slika:</label>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.slika" /><br />
        </div>
        <label>Opis:</label>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.opis" /><br />
        </div>
        <label>Datum izdavanja:</label>
        <div class="form-group">
            <input type="date" v-model="knjigaDto.datum" :max="new Date().toISOString().substring(0, 10)" /><br />
        </div>
        <label>Broj strana:</label>
        <div class="form-group">
            <input type="number" v-model="knjigaDto.brStr" min="1" /><br />
        </div>
        <label>Žanr:</label>
        <div class="form-group">
            <input type="text" v-model="knjigaDto.zanr" required /><br />
        </div>
        <div v-if="uloga == 'ADMINISTRATOR'">
            <label>ID autora:</label>
            <div class="form-group">
                <input type="number" v-model="knjigaDto.autor" required /><br />
            </div>
        </div>
        <button type="submit" class="submit-button">Izmeni</button>
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
                datum: null,
                brStr: '',
                zanr: '',
                autor: ''
            },
            errorMessage: '',
            uloga: '',
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            axios.get('http://localhost:8080/api/knjiga/' + this.$route.query.knjiga_id + '/' + localStorage.getItem('korisnik'))
                .then(response => {
                    console.log(response.data);
                    this.knjigaDto.naslov = response.data.knjiga.naslov;
                    this.knjigaDto.slika = response.data.knjiga.slika;
                    this.knjigaDto.opis = response.data.knjiga.opis;
                    this.knjigaDto.datum = response.data.knjiga.datum;
                    this.knjigaDto.brStr = response.data.knjiga.brStr;
                    this.knjigaDto.zanr = response.data.knjiga.zanr.naziv;
                    this.knjigaDto.autor = response.data.knjiga.autor.id;
                    this.loading = false;
                    this.uloga = this.$route.query.ulog;
                })
                .catch(error => {
                    console.error(error);
                    this.loading = false;
                });
        },
        izmeniKnjigu() {
            axios
                .put('http://localhost:8080/api/knjiga/' + this.$route.query.knjiga_id, this.knjigaDto, { params: { sessionId: localStorage.getItem('korisnik') } })
                .then(response => {
                    console.log(response.data);
                    this.$router.push("/knjiga?id=" + this.$route.query.knjiga_id);
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
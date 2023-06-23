<template>
    <h2 class="subheading">Ažuriraj autora</h2>
    <form @submit.prevent="azurirajAutora" class="form">
        <div class="form-group">
            <input v-model="autorDto.ime" type="text"/>
        </div>
        <div class="form-group">
            <input v-model="autorDto.prezime" type="text"/>
        </div>
        <div class="form-group">
            <input v-model="autorDto.slika" type="text"/>
        </div>
        <div class="form-group">
            <input v-model="autorDto.opis" type="text"/>
        </div>
        <button type="submit" class="submit-button">Ažuriraj</button>
        <p v-if="error" class="error">{{ error }}</p>
    </form>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            autorDto: {
                ime: '',
                prezime: '',
                slika: '',
                opis: ''
            },
            error: ''
        };
    },
    mounted(){
        this.fetchData()
    },
    methods: {
        fetchData() {
            axios.get('http://localhost:8080/api/korisnik/' + this.$route.query.id + '/' + localStorage.getItem('korisnik'))
                .then(response => {
                    console.log(response.data);
                    this.autorDto.ime = response.data.korisnik.ime;
                    this.autorDto.prezime = response.data.korisnik.prezime;
                    this.autorDto.slika = response.data.korisnik.profilnaSlika;
                    this.autorDto.opis = response.data.korisnik.opis;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        azurirajAutora(){
            axios.put('http://localhost:8080/api/autor/' + this.$route.query.id, this.autorDto, {params: {sessionId: localStorage.getItem('korisnik')}})
                .then(response => {
                    console.log(response.data);
                    this.$router.go(-1);
                })
                .catch(error => {
                    console.error(error);
                    if(error.respone && error.respone.data)
                        this.error = error.response.data;
                });
        }
    }
};
</script>

<style scoped>

.form-group {
    margin-bottom: 20px;
}

input[type="text"] {
    width: 25%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
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
    color: red;
}
</style>
<template>
    <div>
        <h2 class="subheading">Zahtev za aktivaciju naloga autora</h2>
        <form @submit.prevent="zahtev" class="form">
            <div class="form-group">
                <input v-model="zahtevDto.mejl" type="email" placeholder="Mejl" required>
            </div>
            <div class="form-group">
                <input v-model="zahtevDto.telefon" type="tel" placeholder="Telefon" required>
            </div>
            <div class="form-group">
                <textarea v-model="zahtevDto.poruka" placeholder="Poruka" required></textarea>
            </div>
            <button type="submit" class="submit-button">Pošalji</button>
            <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            zahtevDto: {
                mejl: '',
                telefon: '',
                poruka: ''
            },
            errorMessage: '',
        };
    },
    methods: {
        zahtev() {
            axios
                .post('http://localhost:8080/api/zahtev/' + this.$route.query.id, this.zahtevDto)
                .then(response => {
                    console.log(response.data);
                    this.$router.push("/korisnik?id=" + this.$route.query.id);
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
.subheading {
    color: #007bff;
    font-size: 20px;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 20px;
}

input[type="email"],
input[type="tel"] {
    width: 15%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
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
    width: 5%;
    padding: 10px;
    font-size: 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-button:hover{
    background-color: darkblue;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>
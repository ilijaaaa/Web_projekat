<template>
    <div v-if="loading" class="loading">Učitavanje...</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <h1 class="header">Žanrovi</h1>
    <ul class="genre-list">
        <li v-for="zanr in zanrovi" :key="zanr.id" class="genre-item">
            <a :href="'/knjigeZanr?naziv=' + zanr.naziv" style="color: #007bff;">
                {{ zanr.naziv }}
            </a>
        </li>
    </ul>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            loading: false,
            error: '',
            zanrovi: [],
        };
    },
    methods: {
        pregledZanrova() {
            this.loading = true;
            axios
                .get('http://localhost:8080/api/zanrovi')
                .then(response => {
                    this.zanrovi = response.data;
                })
                .catch(error => {
                    console.error(error);
                    this.error = error.response.data;
                })
                .finally(() => {
                    this.loading = false;
                });
        },
    },
    mounted() {
        this.pregledZanrova();
    },
};
</script>

<style scoped>
.loading {
    font-size: 20px;
    color: #888;
    margin: 20px;
    background-color: #f5f5f5;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
}

.error {
    font-size: 20px;
    color: #f00;
    margin: 20px;
    background-color: #f8d7da;
    padding: 10px;
}

.header {
    color: #007bff;
    font-size: 75px;
    font-weight: bold;
    margin: 20px;
    background-color: #f5f5f5;
    padding: 20px;
}

.genre-list {
    list-style-type: none;
    padding: 0;
    margin: 0 20px;
    background-color: #f5f5f5;
    padding: 20px;
}

.genre-item {
    font-size: 50px;
    font-weight: bold;
    margin-bottom: 10px;
    background-color: #fff;
    padding: 10px;
}
</style>
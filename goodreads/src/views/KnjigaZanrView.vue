<template>
    <div class="container">
        <h2 style="color: #007bff">Knjige sa žanrom {{ this.$route.query.naziv }}:</h2>

        <div v-if="loading" class="loading">Učitavanje...</div>
        <div v-else-if="errorMessage" class="error">{{ errorMessage }}</div>

        <div v-if="knjige.length > 0">
            <ul class="book-list">
                <li v-for="knjiga in knjige" :key="knjiga.id" class="book-item">
                    <div class="book-image-info">
                        <a :href="'/knjiga?id=' + knjiga.id">
                            <img class="book-image" :src="knjiga.slika" :alt="knjiga.naslov" :title="knjiga.naslov" />
                        </a>
                        <div class="book-info">
                            <h2 class="book-title">{{ knjiga.naslov }}</h2>
                            <h2 class="author-name">{{ knjiga.autor.ime }} {{ knjiga.autor.prezime }}</h2>
                            <vue-star-rating :rating="knjiga.ocena" :increment=0.01 read-only
                                :fixed-points=2></vue-star-rating>
                            <h4 class="book-published">Izdato: {{ knjiga.datum }}</h4>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
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
            loading: false,
            errorMessage: '',
            knjige: []
        };
    },
    mounted() {
        this.pretragaKnjige();
    },
    methods: {
        pretragaKnjige() {
            this.loading = true;
            this.errorMessage = '';
            this.knjige = [];
            axios
                .get('http://localhost:8080/api/knjige/zanr/' + this.$route.query.naziv)
                .then(response => {
                    this.knjige = response.data;
                })
                .catch(error => {
                    console.log(error);
                    if (error.response && error.response.data)
                        this.errorMessage = error.response.data;
                })
                .finally(() => {
                    this.loading = false;
                });
        }
    }
};
</script>

<style scoped>
.loading {
    font-size: 20px;
    color: #888;
}

.error {
    font-size: 20px;
    color: #f00;
}

.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #f2f2f2;
}

.search-bar {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
    background-color: #e6e6e6;
}

input[type='text'] {
    padding: 10px;
    font-size: 16px;
    border-radius: 4px 0 0 4px;
    border: 1px solid #ccc;
    width: 300px;
}

button {
    padding: 10px 20px;
    font-size: 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 0 4px 4px 0;
    cursor: pointer;
}

.book-list {
    padding: 0;
    margin: 0;
    background-color: #f9f9f9;
}

.book-item {
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    background-color: #ffffff;
}

.book-image-info {
    display: flex;
    align-items: flex-start;
}

.book-image {
    width: 150px;
    height: 215px;
    margin-right: 20px;
    transition: transform 0.3s ease-in-out;
}

.book-image:hover {
    transform: scale(1.1);
}

.book-info {
    padding-right: 20px;
    display: flex;
    flex-direction: column;
    align-items: left;
    justify-content: center;
    text-align: left;
}

.book-title {
    color: #007bff;
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
    background-color: #f9f9f9;
}

.author-name {
    font-size: 18px;
    margin-bottom: 10px;
    background-color: #f9f9f9;
}

.book-published {
    font-size: 14px;
    margin-bottom: 5px;
    background-color: #f9f9f9;
}
</style>
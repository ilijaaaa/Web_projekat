<template>
    <div v-if="loading" class="loading">Učitavanje...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else class="book-details">
        <div class="book-image-info">
            <img class="book-image" :src="knjiga.slika" :alt="knjiga.naslov" :title="knjiga.naslov" />
            <div class="book-info">
                <h2 class="book-title">{{ knjiga.naslov }}</h2>
                <h2 class="author-name">{{ autor.ime }} {{ autor.prezime }}</h2>
                <vue-star-rating :rating="knjiga.ocena" :increment=0.01 read-only :fixed-points=2></vue-star-rating>
                <h3 class="book-description">{{ knjiga.opis }}</h3>
                <h3 class="book-genre">{{ zanr.naziv }}</h3>
                <h4 class="book-pages">Broj strana: {{ knjiga.brStr }}</h4>
                <h4 class="book-published">Izdato: {{ knjiga.datum }}</h4>
                <h5 class="book-isbn">ISBN: {{ knjiga.isbn }}</h5>
                <h2 class="author-title">O autoru:</h2>
                <a :href="'/korisnik?id=' + autor.id">
                    <img class="author-image" :src="autor.profilnaSlika" alt="Autorska slika" />
                </a>
                <h2 class="author-name">{{ autor.ime }} {{ autor.prezime }}</h2>
                <h3 class="author-description">Opis: {{ autor.opis }}</h3>
            </div>
        </div>
        <h2 class="reviews-title">Recenzije:</h2>
        <ul class="reviews-list">
            <li v-for="recenzija in recenzije" :key="recenzija.id" class="review-item">
                <a :href="'/korisnik?id=' + recenzija.korisnik.id">
                    <img :src="recenzija.korisnik.profilnaSlika" alt="Korisnička slika" class="user-image" />
                </a>
                <p class="username">{{ recenzija.korisnik.korisnickoIme }}</p>
                <vue-star-rating :rating="recenzija.ocena" :show-rating="false" read-only></vue-star-rating>
                <p class="review-text">{{ recenzija.tekst }}</p>
                <p class="review-date">{{ recenzija.datum }}</p>
            </li>
        </ul>
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
            knjiga: {},
            autor: {},
            zanr: {},
            recenzije: [],
            loading: true,
            error: null,
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            axios.get('http://localhost:8080/api/knjiga/' + this.$route.query.id)
                .then(response => {
                    console.log(response.data);
                    this.knjiga = response.data.knjiga;
                    this.autor = response.data.knjiga.autor;
                    this.zanr = response.data.knjiga.zanr;
                    this.recenzije = response.data.recenzije;
                    this.loading = false;
                })
                .catch(error => {
                    console.error(error);
                    this.error = 'Knjiga ne postoji';
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

ul {
    list-style-type: none;
}

.book-details {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.book-image-info {
    background-color: #f7f7f7;
    display: flex;
    align-items: flex-start;
}

.book-image {
    width: 450px;
    height: 650px;
    margin-right: 20px;
}

.book-info {
    padding-right: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
}

.book-details {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.book-title {
    color: #007bff;
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
}

.author-name {
    font-size: 18px;
    margin-bottom: 10px;
}

.book-description {
    font-size: 16px;
    margin-bottom: 10px;
}

.book-genre {
    font-size: 16px;
    margin-bottom: 10px;
}

.book-pages {
    font-size: 14px;
    margin-bottom: 5px;
}

.book-published {
    font-size: 14px;
    margin-bottom: 5px;
}

.book-isbn {
    font-size: 14px;
    margin-bottom: 10px;
}

.author-title {
    color: #007bff;
    font-size: 20px;
    font-weight: bold;
    margin-top: 20px;
    margin-bottom: 10px;
}

.author-image {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 10px;
    transition: transform 0.3s ease;
}

.author-image:hover {
    transform: scale(1.1);
}

.author-description {
    font-size: 16px;
    margin-bottom: 10px;
}

.reviews-title {
    color: #007bff;
    font-size: 20px;
    font-weight: bold;
    margin-top: 30px;
    margin-bottom: 10px;
}

.reviews-list {
    background-color: #f9f9f9;
    list-style-type: none;
    padding: 0;
    margin: 0;
}

.review-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.user-image {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
    transition: transform 0.3s ease;
}

.user-image:hover {
    transform: scale(1.1);
}

.username {
    font-size: 16px;
    font-weight: bold;
    margin-right: 10px;
}

.review-rating {
    font-size: 14px;
    font-weight: bold;
    margin-right: 10px;
}

.review-text {
    font-size: 14px;
    margin-bottom: 5px;
}

.review-date {
    font-size: 12px;
    color: gray;
}
</style>
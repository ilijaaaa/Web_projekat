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
                <div v-if="autor.aktivan == false && uloga == 'ADMINISTRATOR'">
                    <button class="izmeni-knjigu-button"
                        @click="this.$router.push('/azuriranjeAutora?id=' + autor.id)">Ažuriraj autora</button>
                </div>
            </div>
        </div>
        <div id="outer">
            <div class="inner" v-if="sessionId">
                <button class="dodaj-knjigu-button" @click="this.$router.push('/profil?knjiga_id=' + knjiga.id)">Dodaj na
                    policu</button>
            </div>
            <div class="inner" v-if="uloga == 'ADMINISTRATOR' || id == autor.id">
                <button class="izmeni-knjigu-button"
                    @click="this.$router.push({ path: 'izmenaKnjige', query: { ulog: uloga, knjiga_id: knjiga.id } })">Izmeni
                    knjigu</button>
            </div>
            <div class="inner" v-if="uloga == 'ADMINISTRATOR'">
                <button class="izbrisi-knjigu-button" @click="obrisiKnjigu(knjiga.id)">Izbriši knjigu</button>
            </div>
        </div>
        <div v-if="errorKnjiga" class="error">{{ errorKnjiga }}</div>
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
                <div v-if="recenzija.korisnik.sessionId == this.sessionId && this.sessionId != null">
                    <button class="dodaj-knjigu-button"
                        @click="this.$router.push({ path: 'izmenaRecenzije', query: { id: recenzija.id, knjiga_id: knjiga.id } })"
                        style="margin-left: 10px; margin-bottom: 5px;">Izmeni</button>
                </div>
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
            uloga: null,
            id: '',
            loading: true,
            error: null,
            errorKnjiga: null,
            sessionId: '',
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            axios.get('http://localhost:8080/api/knjiga/' + this.$route.query.id + '/' + localStorage.getItem('korisnik'))
                .then(response => {
                    console.log(response.data);
                    this.knjiga = response.data.knjiga;
                    this.autor = response.data.knjiga.autor;
                    this.zanr = response.data.knjiga.zanr;
                    this.recenzije = response.data.recenzije;
                    this.uloga = response.data.uloga;
                    this.id = response.data.id;
                    this.sessionId = localStorage.getItem("korisnik");
                    this.loading = false;
                })
                .catch(error => {
                    console.error(error);
                    this.error = 'Knjiga ne postoji';
                    this.loading = false;
                });
        },
        obrisiKnjigu(id) {
            axios.delete('http://localhost:8080/api/knjiga/' + id + '/' + this.sessionId)
                .then(response => {
                    console.log(response.data);
                    this.$router.go(-1);
                })
                .catch(error => {
                    console.error(error);
                    this.errorKnjiga = 'Knjiga ima recenzije';
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

#outer {
    width: 100%;
    text-align: center;
}

.inner {
    display: inline-block;
}

.dodaj-knjigu-button,
.izmeni-knjigu-button,
.izbrisi-knjigu-button {
    display: inline;
    margin-top: 10px;
    font-weight: bold;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.dodaj-knjigu-button {
    background-color: green;
}

.dodaj-knjigu-button:hover {
    background-color: darkgreen;
}

.izmeni-knjigu-button {
    margin-left: 1px;
    background-color: blue;
}

.izmeni-knjigu-button:hover {
    background-color: darkblue;
}

.izbrisi-knjigu-button {
    margin-left: 1px;
    background-color: red;
}

.izbrisi-knjigu-button:hover {
    background-color: darkred;
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
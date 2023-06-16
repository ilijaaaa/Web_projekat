<template>
  <div class="profile">
    <div v-if="loading" class="loading">Učitavanje...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <div class="username">
        <h2>{{ korisnik.korisnickoIme }}</h2>
      </div>
      <div class="profile-picture">
        <img :src="korisnik.profilnaSlika" alt="Korisnička slika" />
        <div class="overlay"></div>
      </div>
      <div>
        <h3>{{ korisnik.ime }} {{ korisnik.prezime }}</h3>
      </div>
      <div v-if="korisnik.sessionId == this.sessionId">
        <button @click="logout" class="logout-button">Odjava</button>
      </div>
      <div v-if="korisnik.opis">
        <h3>Opis: {{ korisnik.opis }}</h3>
      </div>
      <div class="bookshelves">
        <h3>Police:</h3>
        <ul>
          <li v-for="polica in police" :key="polica.id" class="bookshelf">
            <div class="bookshelf-title">
              <h4 style="color: #007bff">{{ polica.naziv }}</h4>
            </div>
            <ul class="book-list">
              <li v-for="knjiga in polica.knjige" :key="knjiga.id" class="book-item">
                <a :href="'/knjiga?id=' + knjiga.id">
                  <img class="book-cover" :src="knjiga.slika" :alt="knjiga.naslov" :title="knjiga.naslov" /><br />
                </a>
                <div>
                  <h5>{{ knjiga.autor.ime }} {{ knjiga.autor.prezime }}</h5>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      korisnik: null,
      police: [],
      loading: true,
      error: null,
      sessionId: '',
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      axios.get('http://localhost:8080/api/korisnik/' + this.$route.query.id)
        .then(response => {
          console.log(response.data);
          this.korisnik = response.data.korisnik;
          this.police = response.data.police;
          this.loading = false;
          this.sessionId = localStorage.getItem("korisnik");
        })
        .catch(error => {
          console.error(error);
          this.error = 'Niste prijavljeni';
          this.loading = false;
        });
    },
    logout() {
      axios.post('http://localhost:8080/api/logout', this.sessionId)
        .then(response => {
          console.log(response.data);
          localStorage.clear();
          this.$router.push("/");
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>

<style scoped>
ul {
  list-style-type: none;
}

.profile {
  margin: 20px;
  background-color: #f2f2f2;
  border-radius: 50px;
}

.loading {
  font-size: 20px;
  color: #888;
}

.error {
  font-size: 20px;
  color: #f00;
}

.username {
  color: #007bff;
  padding-top: 5px;
  margin-bottom: 10px;
}

.profile-picture {
  width: 200px;
  height: 200px;
  margin: 0 auto;
  position: relative;
  overflow: hidden;
  border-radius: 50px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  height: 200px;
}

.profile-picture img {
  width: auto;
  height: 90%;
  padding-top: 10px;
  transition: opacity 0.3s ease-in-out;
}

.profile-picture .overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}

.profile-picture:hover img {
  opacity: 0.7;
}

.profile-picture:hover .overlay {
  opacity: 1;
}

.logout-button {
  background-color: #ff0000;
  font-weight: bold;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.logout-button:hover {
  background-color: #cc0000;
}

.bookshelves {
  padding-top: 5px;
  background-color: #fff;
}

.bookshelf {
  text-align: center;
  margin-left: -40px;
  background-color: #f9f9f9;
  padding: 10px;
  margin-bottom: 20px;
  border-radius: 50px;
}

.bookshelf-title {
  margin-bottom: 10px;
}

.book-list {
  list-style-type: none;
  padding-left: 0;
}

.book-item {
  display: inline-block;
  margin-right: 10px;
}

.book-cover {
  width: 100px;
  height: 125px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease-in-out;
}

.book-cover:hover {
  transform: scale(1.1);
}
</style>
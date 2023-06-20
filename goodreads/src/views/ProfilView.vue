<template>
  <div class="profile">
    <div v-if="loading" class="loading">Učitavanje...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <div class="username">
        <h2>{{ korisnik.korisnickoIme }}</h2>
      </div>
      <div class="profile-picture">
        <a :href="'/azuriranjeProfila'">
          <img :src="korisnik.profilnaSlika" alt="Korisnička slika" />
        </a>
        <div class="overlay"></div>
      </div>
      <div>
        <h3>{{ korisnik.ime }} {{ korisnik.prezime }}</h3>
      </div>
      <div>
        <button @click="logout" class="logout-button">Odjava</button>
      </div>
      <div v-if="korisnik.uloga == 'ADMINISTRATOR'">
        <button @click="this.$router.push('/dodajAutora')" class="dodaj-knjigu-button">Dodaj autora</button>
      </div>
      <div v-if="korisnik.uloga != 'CITALAC'">
        <button @click="this.$router.push('/dodajKnjigu')" class="dodaj-knjigu-button">Dodaj knjigu</button>
      </div>
      <div v-if="korisnik.opis">
        <h3>Opis: {{ korisnik.opis }}</h3>
      </div>
      <div class="bookshelves">
        <h3>Police:</h3>
        <div v-if="errorDodavanjePolice" class="error" style="margin-top: 5px;">{{ errorDodavanjePolice }}</div>
        <input type="text" v-model="novaPolica" placeholder="Naziv" @keydown.enter="dodajPolicu" />
        <button @click="dodajPolicu" class="dodaj-policu-button">Dodaj</button>
        <div
          v-if="this.$route.fullPath.substring(this.$route.fullPath.length - 11, this.$route.fullPath.length - 2) == 'knjiga_id'">
          <button @click="this.$router.push('/knjiga?id=' + this.$route.fullPath.charAt(this.$route.fullPath.length - 1))"
            class="logout-button" style="margin-top: 10px;">Odustani</button>
        </div>
        <ul>
          <li v-for="polica in police" :key="polica.id" class="bookshelf">
            <div class="bookshelf-title">
              <div style="display: flex; align-items: center; justify-content: center;">
                <h4 style="color: #007bff">{{ polica.naziv }}</h4>
                <button v-if="polica.primarno == false" @click="ukloniPolicu(polica.id)" class="logout-button"
                  style="margin-left: 10px;">Izbriši</button>
              </div>
              <div
                v-if="this.$route.fullPath.substring(this.$route.fullPath.length - 11, this.$route.fullPath.length - 2) == 'knjiga_id'">
                <button class="dodaj-knjigu-button"
                  @click="dodajKnjiguNaPolicu(polica.id, polica.naziv, this.$route.fullPath.charAt(this.$route.fullPath.length - 1))">Dodaj ovde</button>
              </div>
            </div>
            <ul class="book-list">
              <li v-for="knjiga in polica.knjige" :key="knjiga.id" class="book-item">
                <a :href="'/knjiga?id=' + knjiga.id">
                  <img class="book-cover" :src="knjiga.slika" :alt="knjiga.naslov" :title="knjiga.naslov" /><br />
                </a>
                <div>
                  <h5>{{ knjiga.autor.ime }} {{ knjiga.autor.prezime }}</h5>
                </div>
                <button @click="ukloniKnjiguSaPolice(polica.id, knjiga.id)" class="logout-button">Ukloni</button>
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
      errorDodavanjePolice: '',
      knjiga_id: '',
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      axios.get('http://localhost:8080/api/profil/' + this.$route.query.sessionId)
        .then(response => {
          console.log(response.data);
          this.korisnik = response.data.korisnik;
          this.police = response.data.police;
          this.loading = false;
        })
        .catch(error => {
          console.error(error);
          this.error = 'Niste prijavljeni';
          this.loading = false;
        });
    },
    logout() {
      axios.post('http://localhost:8080/api/logout', this.$route.query.sessionId)
        .then(response => {
          console.log(response.data);
          localStorage.clear();
          this.$router.push("/");
        })
        .catch(error => {
          console.error(error);
        });
    },
    dodajPolicu() {
      axios
        .post('http://localhost:8080/api/polica', { value: this.novaPolica }, {
          params: {
            sessionId: this.$route.query.sessionId
          }
        })
        .then(response => {
          console.log(response.data);
          this.novaPolica = '';
          this.police.push(response.data);
          this.$router.go();
        })
        .catch(error => {
          console.error(error);
          this.errorDodavanjePolice = error.response.data;
        });
    },
    ukloniPolicu(id) {
      axios.delete('http://localhost:8080/api/polica/' + id + '/' + this.$route.query.sessionId)
        .then(response => {
          console.log(response.data);
          this.$router.go();
        })
        .catch(error => {
          console.error(error);
        });
    },
    dodajKnjiguNaPolicu(polica_id, naziv, knjiga_id) {
      axios.post('http://localhost:8080/api/polica/' + polica_id + '/knjiga/' + this.$route.fullPath.charAt(this.$route.fullPath.length - 1), null, { params: { sessionId: this.$route.query.sessionId } })
        .then(response => {
          console.log(response.data);
          if (naziv == 'Read')
            this.$router.push('/recenzija?knjiga_id=' + knjiga_id);
          else
            this.$router.go(-1);
        })
        .catch(error => {
          console.error(error);
          alert(error.response.data)
        });
    },
    ukloniKnjiguSaPolice(polica_id, knjiga_id) {
      axios.delete('http://localhost:8080/api/polica/' + polica_id + '/knjiga/' + knjiga_id + '/' + this.$route.query.sessionId)
        .then(response => {
          console.log(response.data);
          this.$router.go();
        })
        .catch(error => {
          console.error(error);
        });
    },
    addAuthor() {
      this.$router.push('/dodavanjeAutora?sessionId=' + this.$route.query.sessionId);
    },
  }
};
</script>
  
<style scoped>
ul {
  list-style-type: none;
}

.profile {
  margin: 20px auto;
  background-color: #f2f2f2;
  border-radius: 50px;
  text-align: center;
}

.loading {
  font-size: 20px;
  color: #888;
  margin-top: 20px;
}

.dodaj-policu-button {
  background-color: #007bff;
  font-weight: bold;
  color: #fff;
  padding: 4px 15px;
  border: none;
  border-radius: 0 4px 4px 0;
  cursor: pointer;
  margin-top: 20px;
}

.error {
  font-size: 20px;
  color: #f00;
  margin-top: 20px;
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

.dodaj-knjigu-button {
  margin-top: 10px;
  background-color: green;
  font-weight: bold;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.dodaj-knjigu-button:hover {
  background-color: darkgreen;
}

.book-list {
  list-style-type: none;
  padding-left: 0;
}

.book-item {
  margin-left: 12px;
  display: inline-block;
  margin-right: 10px;
  background-color: white;
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
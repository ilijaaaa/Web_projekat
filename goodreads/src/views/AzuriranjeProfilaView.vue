<template>
  <div>
    <h2 class="heading" style="color: #007bff;">Ažuriranje profila</h2>
    <form @submit.prevent="azurirajProfil">
      <label>Ime:</label>
      <div class="form-group">
        <input type="text" v-model="azuriranjeKorisnikaDto.ime" placeholder="Ime" /><br />
      </div>
      <label>Prezime:</label>
      <div class="form-group">
        <input type="text" v-model="azuriranjeKorisnikaDto.prezime" placeholder="Prezime" /><br />
      </div>
      <label>Profilna slika:</label>
      <div class="form-group">
        <input type="text" v-model="azuriranjeKorisnikaDto.profilnaSlika" placeholder="Profilna slika" /><br />
      </div>
      <label>Datum rođenja:</label>
      <div class="form-group">
        <input type="date" v-model="azuriranjeKorisnikaDto.datumRodjenja" placeholder="Datum rođenja" :max="new Date().toISOString().substring(0, 10)"/><br />
      </div>
      <label>Opis:</label>
      <div class="form-group">
        <textarea v-model="azuriranjeKorisnikaDto.opis"></textarea><br />
      </div>
      <div class="form-group">
        <input type="password" v-model="azuriranjeKorisnikaDto.lozinka" placeholder="Nova lozinka" /><br />
      </div>
      <div class="form-group">
        <input type="password" v-model="azuriranjeKorisnikaDto.staraLozinka" placeholder="Trenutna lozinka" /><br />
      </div>
      <div class="form-group">
        <input type="email" v-model="azuriranjeKorisnikaDto.mejl" placeholder="Mejl" /><br />
      </div>
      <button type="submit" class="submit-button">Ažuriraj</button>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </form>
  </div>
</template>
  
<script>
import axios from 'axios';

export default {
  data() {
    return {
      azuriranjeKorisnikaDto: {
        ime: null,
        prezime: null,
        profilnaSlika: null,
        datumRodjenja: null,
        opis: null,
        lozinka: null,
        staraLozinka: null,
        mejl: null,
      },
      errorMessage: '',
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      axios.get('http://localhost:8080/api/profil/' + localStorage.getItem('korisnik'))
        .then(response => {
          console.log(response.data);
          this.azuriranjeKorisnikaDto = response.data.korisnik;
        })
        .catch(error => {
          console.error(error);
        });
    },
    azurirajProfil() {
      axios
        .put('http://localhost:8080/api/korisnik', this.azuriranjeKorisnikaDto, { params: { sessionId: localStorage.getItem('korisnik') } })
        .then(response => {
          this.response = response.data;
          this.$router.go(-1);
        })
        .catch(error => {
          console.error(error);
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
input[type="email"],
input[type="password"] {
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
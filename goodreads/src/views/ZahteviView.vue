<template>
  <div class="zahtevi-container">
    <h1 class="zahtevi-heading" style="color: #007bff;">Zahtevi</h1>
    <div v-if="zahtevi.length === 0" class="empty-zahtevi">
      <p>Nema zahteva</p>
    </div>
    <div v-else>
      <ul class="zahtevi-list">
        <li v-for="zahtev in zahtevi" :key="zahtev.id" class="zahtev-item">
          <div class="zahtev-details">
            <p class="zahtev-info"><span class="info-label">Mejl:</span> {{ zahtev.mejl }}</p>
            <p class="zahtev-info"><span class="info-label">Telefon:</span> {{ zahtev.telefon }}</p>
            <p class="zahtev-info"><span class="info-label">Poruka:</span> {{ zahtev.poruka }}</p>
            <p class="zahtev-info"><span class="info-label">Datum:</span> {{ zahtev.datum }}</p>
            <p class="zahtev-info"><span class="info-label">Status:</span> {{ zahtev.status }}</p>
            <p class="zahtev-info"><span class="info-label">Autor:</span> {{ zahtev.autor.ime }} {{ zahtev.autor.prezime }}</p>
          </div>
          <div class="zahtev-actions">
            <button class="btn-prihvati" @click="prihvati(zahtev)">Prihvati</button>
            <button class="btn-odbij" @click="odbij(zahtev)">Odbij</button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      zahtevi: [],
    };
  },
  mounted() {
    this.fetchZahtevi();
  },
  methods: {
    fetchZahtevi() {
      axios
        .get('http://localhost:8080/api/zahtevi/' + localStorage.getItem('korisnik'))
        .then((response) => {
          this.zahtevi = response.data;
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    },
    prihvati(zahtev) {
      axios
        .put(
          'http://localhost:8080/api/zahtev/odobri/' + zahtev.id,
          null,
          { params: { sessionId: localStorage.getItem('korisnik') } }
        )
        .then((response) => {
          console.log(response);
          this.$router.go(0);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    },
    odbij(zahtev) {
      axios
        .put(
          'http://localhost:8080/api/zahtev/odbij/' + zahtev.id,
          null,
          { params: { sessionId: localStorage.getItem('korisnik') } }
        )
        .then((response) => {
          console.log(response);
          this.$router.go(0);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    },
  },
};
</script>

<style scoped>
.zahtevi-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.zahtevi-heading {
  font-size: 24px;
  margin-bottom: 20px;
}

.empty-zahtevi {
  margin-top: 20px;
  text-align: center;
  color: #777;
}

.zahtevi-list {
  list-style-type: none;
  padding: 0;
}

.zahtev-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  padding: 10px 0;
}

.zahtev-details {
  flex-grow: 1;
}

.zahtev-info {
  margin: 5px 0;
}

.info-label {
  font-weight: bold;
}

.zahtev-actions {
  display: flex;
}

.btn-prihvati,
.btn-odbij {
  padding: 5px 10px;
  margin-left: 10px;
  border: none;
  border-radius: 4px;
  color: #fff;
  font-weight: bold;
  cursor: pointer;
}

.btn-prihvati {
  background-color: #5cb85c;
}

.btn-odbij {
  background-color: #d9534f;
}
</style>
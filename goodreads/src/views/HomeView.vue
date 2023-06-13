<template>
  <div class="container">
    <h1 class="heading">Goodreads</h1>
    <h2 class="subheading">Sign In</h2>
    <form @submit.prevent="submitSignIn" class="form">
      <div class="form-group">
        <input type="text" v-model="signInData.ime" placeholder="Ime" required />
      </div>
      <div class="form-group">
        <input type="text" v-model="signInData.prezime" placeholder="Prezime" required />
      </div>
      <div class="form-group">
        <input type="text" v-model="signInData.korisnickoIme" placeholder="Korisničko ime" required />
      </div>
      <div class="form-group">
        <input type="email" v-model="signInData.mejl" placeholder="Mejl" required />
      </div>
      <div class="form-group">
        <input type="password" v-model="signInData.lozinka" placeholder="Lozinka" required />
      </div>
      <div class="form-group">
        <input type="password" v-model="signInData.ponovljenaLozinka" placeholder="Ponovljena lozinka" required />
      </div>
      <button type="submit" class="submit-button">Sign In</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      signInData: {
        ime: '',
        prezime: '',
        korisnickoIme: '',
        mejl: '',
        lozinka: '',
        ponovljenaLozinka: '',
      },
    };
  },
  methods: {
    submitSignIn() {
      fetch('http://localhost:8080/api/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.signInData),
      })
        .then(response => response.json())
        .then(data => {
          console.log(data);
        })
        .catch(error => {
          console.error(error);
        });
    },
  },
};
</script>

<style>
.container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.heading {
  text-align: center;
  font-size: 32px;
  margin-bottom: 20px;
}

.subheading {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
}

.form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 16px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-button {
  width: 105%;
  padding: 8px;
  background-color: #4caf50;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #45a049;
}
</style>
<script setup lang="ts">
import { ref } from 'vue'
import {SelectUserListView} from '../dto/SelectUserListView'
const userId = ref<string>('');
const data = ref<SelectUserListView|null>(null);
const errorStatus = ref(false);
const submitForm = async() => {
   await fetch('http://127.0.0.1:8080/api/likelist/user-select-like-list', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify({
      uid: userId.value
    }),
  }).then(res=>{
    if(!res.ok){
      errorStatus.value=true;
    }
    return res.json()
  }).then(obj=>{
    data.value=obj 
  }).catch(_err=>{
    errorStatus.value=false
  });
  console.log(data.value?.userEmail)
};

</script>

<template>
  <form class="h-full py-2 justify-evenly flex flex-col items-center italic" @submit.prevent="submitForm">
    <label for="userid" class="underline text-2xl px-2" data-required>User Id</label>
    <input class="w-full placeholder-middle items-center text-center rounded-lg border border-indigo-600 px-2" @keyup.enter="submitForm" id="userid" type="text" placeholder="A123456789" :minlength="1" v-model="userId" required />   
  </form>
  
</template>

<style scoped>
</style>

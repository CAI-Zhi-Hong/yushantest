<script setup lang="ts">
import { ref } from 'vue';
defineProps<{ userId: string|undefined, userName: string|null|undefined, userEmail: string|null|undefined }>()
const emits = defineEmits<{ (event:'update:inputUserId', value: string): void }>()
const userIdInput = ref('');

const handleEnter = () => {
  emits('update:inputUserId', userIdInput.value);
};
</script>
<template>
  <div class="h-full py-2 justify-evenly flex items-center italic" >
    <div>
      <label for="inputUserIdL" class="underline text-2xl px-2" data-required>User Id: </label>
      <input id="inputUserIdL" name="inputUserIdL" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 px-2" type="text" placeholder="A123456789" :minlength="1" v-model="userIdInput" @keydown.enter="handleEnter" required />   
    </div>
    <p class="underline text-2xl px-2">User Name:   {{ userName??'' }}</p>
    <p class="underline text-2xl px-2">User Email:  {{ userEmail??'' }}</p>
  </div>
</template>

<style scoped>
</style>

<script setup lang="ts">
import HanderIdSelect from './components/HanderIdSelect.vue'
import {SelectUserListView, UserPortFolio} from './dto/SelectUserListView'
import { ref, watch } from 'vue'

const selectUserData = ref<SelectUserListView|null>(null);
const inputUserId = ref<string>('');
const errorStatus = ref(false);
const createNewProduct = ref<UserPortFolio>({
    account:undefined,
    orderName:undefined,
    feeRate:undefined,
    price:undefined,
    productName:undefined
});

const handleUserIdUpdate = (value: string) => {
  inputUserId.value = value;
};


watch(inputUserId, (newValue, _oldValue) => {
  if(newValue){
    fetchUserData()
  }
});

const createProductEvent = async() => {
  if (createNewProduct.value.productName === undefined) {
    alert('產品名稱不得為空！');
    return;
  }else if (createNewProduct.value.price === undefined) {
    alert('產品價格不得為空！');
    return;
  }else if (createNewProduct.value.feeRate === undefined) {
    alert('手續費費率不得為空！');
    return;
  }else if (createNewProduct.value.account === undefined) {
    alert('扣款帳號不得為空！');
    return;
  }else if (createNewProduct.value.orderName === undefined) {
    alert('購買數量不得為空！');
    return;
  }
  await fetch('http://localhost:8080/api/likelist/user-insert-like-list', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify({
      uid: inputUserId.value,
      productName:createNewProduct.value.productName,
      productPrice:createNewProduct.value.price,
      productFeeRate:createNewProduct.value.feeRate,
      defaultAccount:createNewProduct.value.account,
      orderName:createNewProduct.value.orderName,
    }),
  }).then(async res=>{
    if(!res.ok){
      errorStatus.value=true;
    }else{
      await fetchUserData()
    }    
    return res
  }).catch(_err=>{
    errorStatus.value=false
  });
}

const deleteProductEvent = async( sn:number ) => {
  await fetch('http://localhost:8080/api/likelist/user-delete-like-list', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify({
      uid: inputUserId.value,
      likeListSN: sn
    }),
  }).then(async res=>{
    if(!res.ok){
      errorStatus.value=true;
    }else{
      await fetchUserData()
    }    
    return res
  }).catch(_err=>{
    errorStatus.value=false
  });
}

const fetchUserData = async() => {
   await fetch('http://127.0.0.1:8080/api/likelist/user-select-like-list', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify({
      uid: inputUserId.value
    }),
  }).then(res=>{
    if(!res.ok){
      errorStatus.value=true;
    }
    return res.json()
  }).then(obj=>{
    selectUserData.value=obj 
  }).catch(_err=>{
    errorStatus.value=false
  });
  console.log(selectUserData.value?.userEmail)
};


</script>

<template>
  <div class="h-screen">
    <div class="h-1/6 bg-yellow-50">
      <HanderIdSelect :userId="inputUserId" :userEmail="selectUserData?.userEmail" :userName="selectUserData?.userName"  @update:inputUserId="handleUserIdUpdate"/> 
    </div>

    <div class="bg-pink-100 w-full h-4/6 underline text-2xl px-4 py-4">
      <div v-if="selectUserData?.userAccount">
        <table class="table-auto border border-collapse border-gray-300 bg-green-50 w-full text-center">
          <thead>
            <tr>
              <th></th>
              <th></th>
              <th>金融產品名稱</th>
              <th>單價</th>
              <th>預計購買數量</th>
              <th>手續費費率</th>
              <th>預計總手續費</th>
              <th>預計總金額</th>
              <th>預計扣款帳號</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(data) in selectUserData.userPortfolios" :key="data.sn">
              <td>
                <div @click="deleteProductEvent(data.sn)" class="hover:bg-red-100 cursor-pointer" >Delete</div>
              </td>
              <td>Update</td>
              <td>{{ data.productName }}</td>
              <td>{{ data.price }}</td>
              <td>{{ data.orderName }}</td>
              <td>{{ data.feeRate }}</td>
              <td>{{ data.totalFee }}</td>
              <td>{{ data.totalAmount }}</td>
              <td>{{ data.account }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="w-full h-1/6">
      <div v-if="selectUserData?.userAccount" class="flex flex-col">
        <div class="text-center text-2xl w-full border-b-2 border-indigo-100 py-2">欲新增產品項目(填完後Enter送出)</div>
        <form @keydown.enter="createProductEvent" class="py-2">
          <div class="flex justify-around">
            <div class="flex flex-col">
              <label for="inputProductName" class="underline text-2xl px-2 text-center" data-required>產品名稱: </label>
              <input id="inputProductName" name="inputProductName" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 px-2" type="text" placeholder="金融商品*" v-model="createNewProduct.productName" :minlength="1" required />
            </div>
            <div class="flex flex-col">
              <label for="inputProductPrice" class="underline text-2xl px-2 text-center" data-required>產品價格: </label>
              <input id="inputProductPrice" name="inputProductPrice" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 px-2" type="number" placeholder="1" min="0" v-model="createNewProduct.price" required />
            </div>
            <div class="flex flex-col">
              <label for="inputFeeRate" class="underline text-2xl px-2 text-center" data-required>手續費費率: </label>
              <input id="inputFeeRate" name="inputFeeRate" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 px-2" type="number" step="0.01" placeholder="0.2" min="0" v-model="createNewProduct.feeRate" required />
            </div>
            <div class="flex flex-col">
              <label for="inputAccount" class="underline text-2xl px-2 text-center" data-required>扣款帳號: </label>
              <input id="inputAccount" name="inputAccount" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 px-2" type="text" placeholder="113355799" v-model="createNewProduct.account" :minlength="1" required />   
            </div>
            <div class="flex flex-col">
              <label for="inputOrderName" class="underline text-2xl px-2 text-center" data-required>購買數量: </label>
              <input id="inputOrderName" name="inputOrderName" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 px-2" type="number" step="1" placeholder="1" v-model="createNewProduct.orderName" min="0" required />   
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>  
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>

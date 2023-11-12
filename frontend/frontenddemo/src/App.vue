<script setup lang="ts">
import HanderIdSelect from './components/HanderIdSelect.vue'
import {ChoicePortfolio, SelectUserListView, UserPortFolio} from './dto/SelectUserListView'
import { ref, watch } from 'vue'
const selectUserData = ref<SelectUserListView|null>(null);
const inputUserId = ref<string>('');
const errorStatus = ref(false);

const choiceUpdateData = ref<ChoicePortfolio|null>(null);
let oldData:ChoicePortfolio|null = null;
const choiceUpdateIndex = ref<number|null>(null);

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

const choiceUpdateRow = (data:ChoicePortfolio, index:number)=>{
  choiceUpdateIndex.value=index;
  choiceUpdateData.value={...data};
  oldData={...data};
}

const cancellUpdate = () => {
  choiceUpdateData.value = oldData;
  choiceUpdateIndex.value = null;
  oldData = null;
}

watch(inputUserId, (newValue, _oldValue) => {
  if(newValue){
    fetchUserData()
  }
});

const submitUpdate = async () => {
  if (choiceUpdateData.value?.productName === undefined) {
    alert('欲修改產品名稱不得為空！');
    return;
  }else if (choiceUpdateData.value?.price === undefined) {
    alert('欲修改產品價格不得為空！');
    return;
  }else if (choiceUpdateData.value?.feeRate === undefined) {
    alert('欲修改手續費費率不得為空！');
    return;
  }else if (choiceUpdateData.value?.account === undefined) {
    alert('欲修改扣款帳號不得為空！');
    return;
  }else if (choiceUpdateData.value?.orderName === undefined) {
    alert('欲修改購買數量不得為空！');
    return;
  }

  console.log(choiceUpdateData.value)
  
  await fetch('http://localhost:8080/api/likelist/user-update-like-list', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify({
      uid: inputUserId.value,
      likeListSN: choiceUpdateData.value.sn,
      account: choiceUpdateData.value.account,
      orderName: choiceUpdateData.value.orderName,
      feeRate: choiceUpdateData.value.feeRate,
      price: choiceUpdateData.value.price,
      productName: choiceUpdateData.value.productName     
    }),
  }).then(async res=>{
    if(!res.ok){
      errorStatus.value=true;
      cancellUpdate();
      alert('Update Failed！');
    }else{
      if(selectUserData.value && choiceUpdateIndex.value){
        selectUserData.value.userPortfolios[choiceUpdateIndex.value] = choiceUpdateData.value!
      }
      choiceUpdateIndex.value = null;
    }    
    return res
  }).catch(_err=>{
    errorStatus.value=false
  });}

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
    // }else{
    //   // await fetchUserData()
    }    
    return res.text()
  }).then(obj=>{
    if(selectUserData.value){
      selectUserData.value.userPortfolios = selectUserData.value.userPortfolios.concat({
        account:createNewProduct.value.account! ,
        orderName:createNewProduct.value.orderName!,
        feeRate:createNewProduct.value.feeRate!,
        price:createNewProduct.value.price!,
        productName:createNewProduct.value.productName!,
        sn: obj as unknown as number,
        totalAmount: Math.round(createNewProduct.value.orderName!*createNewProduct.value.price! + createNewProduct.value.feeRate!*createNewProduct.value.price!*createNewProduct.value.orderName!),
        totalFee:Math.round(createNewProduct.value.feeRate!*createNewProduct.value.price!*createNewProduct.value.orderName!),
      })
    }
  }).catch(_err=>{
    errorStatus.value=false
  });
}

const deleteProductEvent = async( sn:number, index:number ) => {
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
      if(selectUserData.value){
        selectUserData.value.userPortfolios.splice(index,1)
      }
    }    
    return res
  }).catch(_err=>{
    errorStatus.value=false
  });
}

// const deleteProductElement=(sn:number)=>{
//   selectUserData.value?.userPortfolios=selectUserData.value!.userPortfolios.filter(obj=>obj.sn!==sn)
// }

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
};


</script>

<template>
  <div class="h-screen">
    <div class="h-1/6 bg-yellow-50">
      <HanderIdSelect :userId="inputUserId" :userEmail="selectUserData?.userEmail" :userName="selectUserData?.userName"  @update:inputUserId="handleUserIdUpdate"/> 
    </div>

    <div class="bg-pink-100 w-full h-4/6 text-2xl px-4 py-4 overflow-y-auto">
      <div v-if="selectUserData?.userAccount">
        <ul>
          <div class="border border-collapse border-gray-300 bg-green-50 w-full flex justify-evenly text-center underline py-2">
            <div class="w-40"></div>
            <div class="w-40"></div>
            <div class="w-40">金融產品名稱</div>
            <div class="w-40">單價</div>
            <div class="w-40">預計購買數量</div>
            <div class="w-40">手續費費率</div>
            <div class="w-40">預計總手續費</div>
            <div class="w-40">預計總金額</div>
            <div class="w-40">預計扣款帳號</div>
          </div>
          <li v-for="(data, index) in selectUserData.userPortfolios" :key="data.sn">
            <div v-if="index!==choiceUpdateIndex" class="border border-collapse border-gray-300 bg-green-50 w-full flex justify-evenly text-center py-2">
              <div class="w-40 hover:bg-red-100 cursor-pointer" @click="deleteProductEvent(data.sn, index)" >Delete</div>
              <div class="w-40 hover:bg-red-100 cursor-pointer" @click="choiceUpdateRow(data, index)">Update</div>
              <div class="w-40">{{ data.productName }}</div>
              <div class="w-40">{{ data.price }}</div>
              <div class="w-40">{{ data.orderName }}</div>
              <div class="w-40">{{ data.feeRate }}</div>
              <div class="w-40">{{ data.totalFee }}</div>
              <div class="w-40">{{ data.totalAmount }}</div>
              <div class="w-40">{{ data.account }}</div>
            </div>
            <div v-if="index==choiceUpdateIndex && choiceUpdateData">
              <div class="border border-collapse border-gray-300 bg-blue-50 w-full flex justify-evenly text-center py-2">
                <div class="w-40 hover:bg-red-100 cursor-pointer" @click="submitUpdate">Submit</div>
                <div class="w-40 hover:bg-red-100 cursor-pointer" @click="cancellUpdate" @keydown.esc="cancellUpdate">Cancelled</div>
                <input id="newProductName" name="newProductName" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600" type="text" v-model="choiceUpdateData.productName" :minlength="1" required>   
                <input id="newProductPrice" name="newProductPrice" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 pl-4" type="number" placeholder="1" min="0" v-model="choiceUpdateData.price" required />
                <input id="newOrderName" name="newOrderName" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 pl-4" type="number" placeholder="1" min="0" v-model="choiceUpdateData.orderName" required />
                <input id="newFeeRate" name="newFeeRate" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600 pl-4" type="number" placeholder="1" min="0" step="0.01" v-model="choiceUpdateData.feeRate" required />
                <div class="w-40">{{ choiceUpdateData.totalFee = Math.round(choiceUpdateData.feeRate * choiceUpdateData.orderName * choiceUpdateData.price)}}</div>
                <div class="w-40">{{ choiceUpdateData.totalAmount = Math.round(choiceUpdateData.feeRate * choiceUpdateData.orderName * choiceUpdateData.price + choiceUpdateData.price * choiceUpdateData.orderName)}}</div>
                <input id="newAccount" name="newAccount" class="w-40 placeholder-middle text-center rounded-lg border border-indigo-600" type="text" v-model="choiceUpdateData.account" :minlength="1" required>   
              </div>
            </div>
          </li>
        </ul>
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

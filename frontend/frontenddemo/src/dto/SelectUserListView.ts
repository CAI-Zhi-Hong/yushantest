export interface SelectUserListView {
    userAccount:string|null,
    userEmail:string|null,
    userPortfolios:{
         sn: number,
         account:String,
         orderName:number,
         totalAmount:number,
         totalFee:number,
         no:number,
         feeRate:number,
         price:number,
         productName:string
    }[]
}
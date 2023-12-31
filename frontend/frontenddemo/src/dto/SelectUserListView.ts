export interface SelectUserListView {
    userAccount:string|null,
    userEmail:string|null,
    userName:string|null,
    userPortfolios:ChoicePortfolio[]
}

export interface UserPortFolio{
    account:String|undefined,
    orderName:number|undefined,
    feeRate:number|undefined,
    price:number|undefined,
    productName:string|undefined
}

export interface ChoicePortfolio{
    account:String,
    orderName:number,
    feeRate:number,
    price:number,
    productName:string,
    sn: number,
    totalAmount:number,
    totalFee:number,
}
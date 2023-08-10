export class Product {
  id: number;
  name: string;
  category: string;
  price: number;
  quantity: number;

  constructor(id: number, name: string, category: string, price: number, quantity: number) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
  }
}

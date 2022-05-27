export class OrderType {

  id: number | null;
  name: string | null;
  description: string | null;

  constructor(id: number | null = 0, name: string | null = null, description: string | null = null) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

}

export class User {
  id: number;
  role: 'ORGA'|'PARTICIPANT';
  email: string;
  password: string;
  constructor(userId: number, role: 'ORGA'|'PARTICIPANT') {
    this.id = userId;
    this.role = role;
  }
}

import { Message } from "@angular/compiler/src/i18n/i18n_ast";
import { Trade } from "./trade";

export class User {

  id: number;
  username: string;
  firstName: string | null;
  lastName: string | null;
  password: string | null;
  enabled: boolean | null;
  role: string;
  email: string | null;
  biography: string | null;
  createdAt: Date | null;
  profilePicture: string | null;
  account: string | null;
  trades: Trade[] | null;
  comments: Comment[] | null;
  sentMessages: Message[] | null;
  recMessages: Message[] | null;

  constructor(  id: number = 0, username: string = "", firstName: string | null = "", lastName: string | null = "",
   password: string | null = "", enabled: boolean | null = true, role: string = "", email: string | null = "",
   biography: string | null = "", createdAt: Date | null = null, profilePicture: string | null = "", account: string | null = "",
    trades: Trade[] | null = [], comments: Comment[] = [], sentMessages: Message[] = [], recMessages: Message[] = []) {

      this.id = id,
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.password = password;
      this.enabled = enabled;
      this.role = role;
      this.email = email;
      this.biography = biography;
      this.createdAt = createdAt;
      this.profilePicture = profilePicture;
      this.account = account;
      this.trades = trades;
      this.comments = comments;
      this.sentMessages = sentMessages;
      this.recMessages = recMessages;
  }

}

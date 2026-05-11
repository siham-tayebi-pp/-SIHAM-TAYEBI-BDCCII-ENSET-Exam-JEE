import { Routes } from '@angular/router';
import { Accounts } from './accounts/accounts';
import { Customers } from './customers/customers';

export const routes: Routes = [
  {    path: 'customers' , component: Customers },
  {    path: 'accounts' , component: Accounts }

];


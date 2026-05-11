# EBankingAppJ2ee

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.2.7.

## Development server

To start a local development server, run:

```bash
ng serve
```
![1.png](images/1.png)
Pour autoriser l'exécution des scripts automatiquement au lieu de confirmer à chaque fois et être obligé d'utiliser PowerShell on peut exécuter une commande
```cmd
Set-ExecutionPolicy RemoteSigned

```

Once the server is running by default with port 4200, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Icons
Pour installer les icons
On va utiliser la commande
```bash
npm install bootstrap --save
npm install bootstrap-icons --save
```
![2.png](images/2.png)
Pour utiliser Bootstrap dans notre application on va ajouter dans angular.json
dans styles

et dans scripts on ajoute

```bash
 "styles": [
              "src/styles.css","node_modules/bootstrap/dist/css/bootstrap.min.css"
            ],
          "scripts":[
            "node_modules/bootstrap/dist/js/bootstrap.bundle.js"
          ]

          }
```

## On copie un navbar depuis le site officiel de Bootstrap et on le colle dans app.component.html
```html
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Dropdown
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="#">Action</a></li>
            <li><a class="dropdown-item" href="#">Another action</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#">Something else here</a></li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
        </li>
      </ul>
      <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
    </div>
  </div>
</nav>
```
On redémarre et voilà notre page template
![3.png](images/3.png)
On crée ou génère des composants pour structurer notre code
on nomme le navbar
Ça génère 4 fichiers d'un composant : html, TypeScript, le module ou bien la classe du composant
fichier .spec.ts pour les tests unitaires

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name / ng g c name-component
```
![4.png](images/4.png)
On copie et colle le code du navbar dans notre page de ce composant
On voit ici qu'il utilise dans navbar.ts que ce composant utilise le selector app-navbar donc on copie et on le colle dans notre page index.html
```ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {}

```
et on met dans index.html

## On génère ces composants customers et accounts
```powershell
ng g c customers
ng g c accounts
```
![5.png](images/5.png)

## app.route.ts
On configure ici nos routes :
```ts
export const routes: Routes = [

];

```
Et voilà nos routes bien configurées
```ts
export const routes: Routes = [
  {    path: 'customers' , component: Customers },
  {    path: 'accounts' , component: Accounts }

];

```
Dans notre navbar on configure les liens
```ts
 <a class="nav-link" routerLink="/accounts">Accounts</a>
  <a class="nav-link" routerLink="/customers">Customers</a>

```
Dans notre composant principal on ajoute router-outlet
On vérifie dans notre page
```ts
<router-outlet></router-outlet>
```

![6.png](images/6.png)
![7.png](images/7.png)
Et voilà les routes marchent bien
Maintenant dans la page customers on veut consulter la page des customers en envoyant une requête au backend via
```thymeleafurlexpressions
http://localhost:8089/customers
```
Pour ce faire on doit utiliser HttpClient

Attention aux imports

```ts
import { HttpClientModule } from '@angular/common/http';

```
et dans les déclarations on ajoute
```ts
HttpClientModule
```
## On va au fichier du composant .ts et il doit hériter de OnInit et injecter dans le constructeur l'objet HttpClient et implémenter la méthode ngOnInit
```ts
// ici attribut customers de type any
 ngOnInit() {
    this.http.get("http://localhost:8080/customers").subscribe({ next : (resp:any) => {
        this.customers = resp;
      },
      error : (error:any) => {
        console.log(error);
      },
    });//pour attendre que les données arrivent
  }
```
## On ajoute une boucle dans notre customer component et il nous affiche une erreur qui dit que notre requête est bloquée par la politique CORS

On ajoute dans notre backend REST API
```text
Car cette erreur signifie qu'un nom de domaine X veut accéder ou envoie des requêtes à un domaine Y et lui il refuse. Pour l'autoriser on met
@CrossOrigin("*")  ça autorise tout mais on peut autoriser que le nom de domaine qu'on veut autoriser
```
Et voilà notre résultat 
![8.png](images/8.png)
## Nous avons aussi créé des services pour customers et pour accounts aussi 
Un service est une classe qui contient de la logique (surtout les appels API) et qui peut être utilisée par plusieurs composants.
via la commande
```cmd
ng g s services/customer
```
Là nous avons mis la logique métier
Toujours GET/PUT dans les services doivent retourner un Observable
On crée notre service
```ts
import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Customer  {

  constructor(private http: HttpClient) { }
  public  getCustomers() :Observable<any> {
     return this.http.get("http://localhost:8089/customers");
  }


}

```
Et on l'injecte dans la page où on veut utiliser ses méthodes et c'est bon, elles marchent 
![8.png](images/8.png)
3 méthodes pour initialiser une variable de type différent de any
```ts
// errorMessage: String="";
  // errorMessage: String | undefined;
  errorMessage!: String ;

```
Pour afficher l'erreur sur la page on ajoute une propriété de type string/object dans ma customer.ts pour stocker l'erreur et l'afficher sur ma page
On met : si elle est non vide elle affiche un message 
```html
 *ngIf="errorMessage"
```
On teste dans le cas où le backend n'est pas disponible
```html
// on met une url fausse dans notre service
return this.http.get("http://localhost:8089/customer");
```
Voilà l'erreur 
![9.png](images/9.png)
On affiche l'erreur sous forme JSON
![10.png](images/10.png)

Voilà le message d'erreur
![11.png](images/11.png)
en mettant
```html
  <div class="card text-danger" *ngIf="errorMessage">
  {{errorMessage  }}
  <!--    {{errorMessage | json}}-->
</div>
```
```ts
error.message
```
## Méthode 3 : retourner dans customers.ts la liste des Observables pas des customers directement
## Dans la page HTML on doit ajouter le pipe async pour qu'une fois les données arrivent il les affiche
Et voilà la 3ème méthode a marché
![12.png](images/12.png)
## On passe à créer un dossier model
```text
Si on veut que lire on déclare un model avec interface 
export interface Customer {
  id:

}
Et si on veut lire et écrire on met class
export class Customer {
 

}

```
Après avoir créé notre classe on va remplacer au lieu de générer any ou array de any avec Customer et array de Customer

```text
Observable<Array<Customer>> 
Customer
```
# On ajoute aussi le cas de gestion d'erreur
```text
this.customers= this.customer.getCustomers().pipe(catchError(
      err => {
        this.errorMessage=err.message
        return throwError(err)
      }
    )); // pipe c'est-à-dire vous écoutez, c'est pour cela il faut envoyer un résultat
```
et dans le code HTML
```html
  <ng-container *ngIf="customers | async as listCustomers; else failure">

</ng-container>
<ng-template #failure class="text-danger">

</ng-template>
```
## On teste les 3 cas : failure, succès et loading
## Failure
![13.png](images/13.png)
## Succès
![14.png](images/14.png)
## Loading
![15.png](images/15.png)


## On importe ce module ReactiveFormsModule, à chaque fois qu'on crée un formulaire on doit créer un objet de type FormGroup
## Nous avons ajouté l'endpoint search pour chercher un customer
## Nous avons créé le formulaire de search via keyword
![16.png](images/16.png)

## On génère le dossier environments où on va mettre nos variables d'environnement
```text
ng generate environments
```
## On teste notre formulaire de search
![17.png](images/17.png)
siham

![18.png](images/18.png)
null ou chaîne vide
![19.png](images/19.png)
filtre qui n'existe pas
![20.png](images/20.png)
## Nous avons utilisé Bootstrap Icons en l'ajoutant dans styles 
```css
/* You can add global styles to this file, and also import other style files */
@import "bootstrap/dist/css/bootstrap.min.css";
@import "bootstrap-icons/font/bootstrap-icons.min.css";

```
## Nous avons redémarré après

![21.png](images/21.png)

## Nous avons généré un nouveau composant
```cmd

ng g c new-customer
```
## Nous avons ajouté la route new-customer et search et les avons configurées

![22.png](images/22.png)
## Nous créons notre formulaire d'ajout de nouveau customer

## On crée la méthode saveCustomer
## Voilà notre formulaire testé et ça marche
![23.png](images/23.png)
![24.png](images/24.png)

## Nous avons aussi mis la suppression, voici le test : on supprime un customer qu'on vient d'ajouter
![25.png](images/25.png)
![26.png](images/26.png)

On teste la validation du formulaire si ça marche 
![27.png](images/27.png)

On ajoute une confirmation à la suppression
![28.png](images/28.png)
## FormControl pour faire le data binding

![29.png](images/29.png)
Si le mot de passe ou le user est incorrect ça va afficher l'erreur 401
![30.png](images/30.png)
## Nous avons stocké les informations du user dans le service auth une fois il est authentifié 
![31.png](images/31.png)
## Nous avons installé jwt-decode pour décoder le token
avec
```text
npm install jwt-decode
```
```text
C'est du code qui intercepte toutes les requêtes HTTP (HttpClient) avant qu'elles partent et/ou quand la réponse revient.

🧠 À quoi ça sert ?

Les interceptors servent surtout à :

1. 🔐 Ajouter automatiquement un token JWT

Au lieu de le mettre dans chaque requête :

Authorization: Bearer <token>

👉 l'interceptor le fait pour toutes les requêtes.
```
## Nous avons généré les interceptors
ng g  interceptor interceptors/app-http
For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

## Nous avons créé les guards
Un Guard (comme CanActivate) sert à contrôler l'accès aux routes.
![32.png](images/32.png)
## Nous avons configuré le logout
## Nous avons affiché le nom du user connecté
![33.png](images/33.png)

## Nous avons généré un composant not-authorized 

![34.png](images/34.png)
## Nous avons ajouté not-authorized pour les méthodes comme ajouter customer etc, il doit afficher notAuthorized 
![35.png](images/35.png)
## On cache new-customer dans le menu déroulant pour ne pas qu'il apparaisse
![36.png](images/36.png)
![37.png](images/37.png)

## Nous avons consulté aussi la search des comptes et leurs opérations et aussi en ajoutant d'autres opérations
![38.png](images/38.png)
## Nous avons aussi ajouté 2 opérations de débit et crédit
![39.png](images/39.png)
## Et une opération de transfer
![40.png](images/40.png)
![41.png](images/41.png)


## Nous avons caché les opérations sur les users normaux et les laissé que pour les admins
via 
```text
<div class="card-header">Operations</div>
        <div class="card-body" *ngIf="this.authService.roles.includes('ADMIN')">

```
![42.png](images/42.png)

## Nous avons stocké notre token dans le localStorage
![43.png](images/43.png)

![44.png](images/44.png)

## On peut partager le secret dans le cas des applications distribuées

## Nous avons aussi ajouté un bouton qui affiche tous les comptes d'un customer
![45.png](images/45.png)
## Nous avons passé à la
Partie 4 : Chat BOT AI
## En utilisant Spring AI pour communiquer avec des LLMs comme LLaMA
## Comment créer un système RAG

Développer un service implémentant un Chatbot basé sur RAG (Retrieval Augmented Generation) avec un client Telegram :


👉 En gros :

Il décide si l'utilisateur a le droit d'entrer dans une page ou non
```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

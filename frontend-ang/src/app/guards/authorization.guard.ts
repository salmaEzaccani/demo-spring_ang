import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  GuardResult,
  MaybeAsync, Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";


//*****  CA C POUR PROTEGER LES ROUTES on va etre sur que cette user est authentifier avant dacceder a une route *****
@Injectable()
export class AuthorizationGuard {

  constructor(private authService : AuthService, private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.authService.isAuthenticated){//si jappel une route ce GUARD va verifier dabord si je suis authentifier
      let requiredRoles = route.data['roles'];//apres avoir authentifier je vais recupere le role et certain route doivent acceder juste pour ADMIN et non STUDENT
      let userRoles=this.authService.roles;
      for(let role of userRoles){
        if(requiredRoles.includes(role)){
          return true;
        }
      }
      return false;
    }
    else{
      this.router.navigateByUrl('/login')
      return false;
    }
  }

}

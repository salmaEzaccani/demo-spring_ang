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


//*****  CA C POUR PROTEGER LES ROUTES  *****
@Injectable()
export class AuthGuard {

  constructor(private authService : AuthService, private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.authService.isAuthenticated){//si jappel une route ce GUARD va verifier dabord si je suis authentifier
      return true;
    }
    else{
      this.router.navigateByUrl('/login')
      return false;
    }
  }

}

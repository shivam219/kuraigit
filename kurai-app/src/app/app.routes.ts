import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
    },
    {
        path: 'register',
        loadComponent: () => import('./auth/register/register.component').then(m => m.RegisterComponent)
    },
    {
        path: 'dashboard',
        loadComponent: () => import('./core/layout/layout.component').then(m => m.LayoutComponent),
        children: [
            {
                path: '',
                redirectTo: 'transaction',
                pathMatch: 'full'
            },
            // {
            //     path: 'transaction',
            //     loadComponent: () =>
            //         import('./dashboard/transaction/transaction.component').then(m => m.TransactionComponent)
            // }
            {
                path: 'kyc',
                loadComponent: () =>
                    import('./kyc/kyc/kyc.component').then(m => m.KycComponent)
            },
            {
                path: 'upi-connect',
                loadComponent: () =>
                    import('./kyc/upi-connect/upi-connect.component').then(m => m.UpiConnectComponent)
            },
            {
                path: 'payment',
                loadComponent: () =>
                    import('./payment/payment/payment.component').then(m => m.PaymentComponent)
            },
            {
                path: 'subscriptions',
                loadComponent: () =>
                    import('./subscription/subscription.component').then(m => m.SubscriptionComponent)
            },
            {
                path: 'subscriptions-add',
                loadComponent: () =>
                    import('./subscription/subsciption-add/subsciption-add.component').then(m => m.SubsciptionAddComponent)
            }            
        ]
    }
];

export const appConfig = [provideRouter(routes)];

package app.demo.Vcalls.di


import app.demo.Vcalls.network.repository.Repository
import app.demo.Vcalls.network.repository.SignUpRepository
import app.demo.Vcalls.network.repository.UserRepository
import app.demo.Vcalls.network.supabase.SupabaseClientProvider
import app.demo.Vcalls.network.viewmodels.AuthViewModel
import app.demo.Vcalls.network.viewmodels.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Provide Supabase Client
    single { SupabaseClientProvider.client }

    // Provide Repositories
    single { Repository(context = get(), supabaseClient = get()) }
    single { SignUpRepository(supabaseClient = get()) }
    single { UserRepository(supabaseClient = get()) }


    // Provide ViewModel
    viewModel { AuthViewModel(get(), get()) }
    viewModel { HomeScreenViewModel(get()) }
}

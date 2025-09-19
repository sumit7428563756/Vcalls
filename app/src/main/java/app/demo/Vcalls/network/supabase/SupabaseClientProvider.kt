package app.demo.Vcalls.network.supabase


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest


object SupabaseClientProvider {

    private const val SUPABASE_URL = "https://ruqqudlbwwnghiemifxc.supabase.co"
    private const val SUPABASE_ANON_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ1cXF1ZGxid3duZ2hpZW1pZnhjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTgxMTY5NTcsImV4cCI6MjA3MzY5Mjk1N30.j6SnOpC0NftceUrFHsnFfBi-PuYejteiV3Uz4PZqEio"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(Auth) {
                alwaysAutoRefresh = true
                autoLoadFromStorage = true
            }
            install(Postgrest)
        }
    }
}

import SwiftUI
import Shared


class AppDelegate: NSObject, UIApplicationDelegate {

    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {

        do {
            print("🚀 Starting Koin initialization...")
            InitKoinKt.doInitKoin()
            print("✅ Koin initialized successfully")
            return true
        } catch {
            print("❌ Koin initialization failed: \(error)")
            print("❌ Error details: \(error.localizedDescription)")
            // Return true to continue app launch and see the error
            return true
        }
    }
}
@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

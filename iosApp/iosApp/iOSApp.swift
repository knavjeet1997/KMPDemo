import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

@main
struct iOSApp: App {
    init() {
        HeaderConfig.shared.deviceType = "ios"
        HeaderConfig.shared.deviceId = "ios_device_id_example_123"
        HeaderConfig.shared.latitude = "18.3123232"
        HeaderConfig.shared.longitude = "73.556723434"
        HeaderConfig.shared.userAgent = "Darwin/Ktor"
        
        AppModuleKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ComposeView()
                .ignoresSafeArea()
        }
    }
}

struct iOSApp_Previews: PreviewProvider {
    static var previews: some View {
        let _ = AppModuleKt.doInitKoin()
        return ComposeView()
            .ignoresSafeArea()
    }
}
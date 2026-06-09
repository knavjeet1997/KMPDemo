import SwiftUI
import Shared

@main
struct iOSApp: App {
    @State private var navigateToHome = false

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
            ZStack {
                if navigateToHome {
                    HomeScreenView()
                        .transition(.asymmetric(
                            insertion: .move(edge: .trailing).combined(with: .opacity),
                            removal: .move(edge: .leading).combined(with: .opacity)
                        ))
                } else {
                    SignupView(onNavigateToHome: {
                        withAnimation(.easeInOut(duration: 0.5)) {
                            navigateToHome = true
                        }
                    })
                    .transition(.asymmetric(
                        insertion: .move(edge: .leading).combined(with: .opacity),
                        removal: .move(edge: .trailing).combined(with: .opacity)
                    ))
                }
            }
        }
    }
}
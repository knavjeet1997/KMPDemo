import SwiftUI
import Shared

enum AppScreen {
    case login
    case signup
    case home
}

@main
struct iOSApp: App {
    @State private var currentScreen: AppScreen = .login

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
                switch currentScreen {
                case .login:
                    LoginView(
                        onNavigateToHome: {
                            withAnimation(.easeInOut(duration: 0.5)) {
                                currentScreen = .home
                            }
                        },
                        onNavigateToSignup: {
                            withAnimation(.easeInOut(duration: 0.5)) {
                                currentScreen = .signup
                            }
                        }
                    )
                    .transition(.asymmetric(
                        insertion: .move(edge: .leading).combined(with: .opacity),
                        removal: .move(edge: .trailing).combined(with: .opacity)
                    ))
                case .signup:
                    SignupView(
                        onNavigateToHome: {
                            withAnimation(.easeInOut(duration: 0.5)) {
                                currentScreen = .home
                            }
                        },
                        onNavigateToLogin: {
                            withAnimation(.easeInOut(duration: 0.5)) {
                                currentScreen = .login
                            }
                        }
                    )
                    .transition(.asymmetric(
                        insertion: .move(edge: .trailing).combined(with: .opacity),
                        removal: .move(edge: .leading).combined(with: .opacity)
                    ))
                case .home:
                    HomeScreenView()
                        .transition(.asymmetric(
                            insertion: .move(edge: .trailing).combined(with: .opacity),
                            removal: .move(edge: .leading).combined(with: .opacity)
                        ))
                }
            }
        }
    }
}
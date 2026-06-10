import SwiftUI
import Shared

class ObservableLoginViewModel: ObservableObject {
    let viewModel: LoginViewModel
    @Published var state: LoginUiState
    @Published var toastMessage: String? = nil
    var onNavigateToHomeCallback: (() -> Void)? = nil

    private var stateWatcher: Closeable? = nil
    private var eventsWatcher: Closeable? = nil

    init(viewModel: LoginViewModel = KoinHelper().getLoginViewModel()) {
        self.viewModel = viewModel
        self.state = viewModel.uiState.value as! LoginUiState
        
        self.stateWatcher = viewModel.uiState.watch { [weak self] newState in
            self?.state = newState as! LoginUiState
        }

        self.eventsWatcher = viewModel.events.watch { [weak self] event in
            switch event {
            case let showToast as LoginUiEventShowToast:
                withAnimation(.easeInOut(duration: 0.4)) {
                    self?.toastMessage = showToast.message
                }
                
                // Automatically hide toast after 3 seconds
                DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) {
                    withAnimation(.easeInOut(duration: 0.4)) {
                        if self?.toastMessage == showToast.message {
                            self?.toastMessage = nil
                        }
                    }
                }
            case is LoginUiEventNavigateToHome:
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.2) {
                    self?.onNavigateToHomeCallback?()
                }
            default:
                break
            }
        }
    }

    deinit {
        stateWatcher?.close()
        eventsWatcher?.close()
    }
}

struct LoginView: View {
    let onNavigateToHome: () -> Void
    let onNavigateToSignup: () -> Void
    @Environment(\.colorScheme) var colorScheme
    @StateObject private var observableViewModel = ObservableLoginViewModel()

    private var gradientStart: Color {
        colorScheme == .dark ? Color(red: 13.0/255.0, green: 27.0/255.0, blue: 42.0/255.0) : Color(red: 227.0/255.0, green: 242.0/255.0, blue: 253.0/255.0)
    }

    private var gradientEnd: Color {
        colorScheme == .dark ? Color(red: 18.0/255.0, green: 18.0/255.0, blue: 18.0/255.0) : Color(red: 249.0/255.0, green: 251.0/255.0, blue: 252.0/255.0)
    }

    private var inputBg: Color {
        colorScheme == .dark ? Color(red: 0.15, green: 0.15, blue: 0.15) : Color(red: 0.95, green: 0.95, blue: 0.95)
    }

    private var inputFg: Color {
        colorScheme == .dark ? Color.white : Color.black
    }

    var body: some View {
        ZStack {
            LinearGradient(
                gradient: Gradient(colors: [gradientStart, gradientEnd]),
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack(spacing: 20) {
                Text("Log In")
                    .font(.system(size: 40, weight: .bold))
                    .foregroundColor(colorScheme == .dark ? .white : .black)
                    .padding(.top, 40)
                    .padding(.bottom, 20)

                // Email Input
                TextField(
                    "",
                    text: Binding(
                        get: { observableViewModel.state.email },
                        set: { observableViewModel.viewModel.onEmailChange(newValue: $0) }
                    ),
                    prompt: Text("Email").foregroundColor(.gray)
                )
                .padding()
                .background(inputBg)
                .foregroundColor(inputFg)
                .cornerRadius(12)
                .textInputAutocapitalization(.none)
                .keyboardType(.emailAddress)

                // Password Input
                SecureField(
                    "",
                    text: Binding(
                        get: { observableViewModel.state.password },
                        set: { observableViewModel.viewModel.onPasswordChange(newValue: $0) }
                    ),
                    prompt: Text("Password").foregroundColor(.gray)
                )
                .padding()
                .background(inputBg)
                .foregroundColor(inputFg)
                .cornerRadius(12)

                // Navigation Link to Signup
                Button(action: {
                    onNavigateToSignup()
                }) {
                    Text("Don't have an account? Sign Up")
                        .font(.system(size: 16))
                        .foregroundColor(.orange)
                        .underline()
                }
                .padding(.top, 10)

                Spacer()

                // Log In Button
                Button(action: {
                    observableViewModel.viewModel.login()
                }) {
                    Text("Log In")
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity, minHeight: 50)
                        .background(Color.blue)
                        .cornerRadius(12)
                }
                .disabled(observableViewModel.state.isLoading)
                .buttonStyle(PressedButtonStyle())
                .padding(.bottom, 40)
            }
            .padding(.horizontal, 24)

            // Custom Toast Notification Overlay
            if let toastMsg = observableViewModel.toastMessage {
                CustomToastView(message: toastMsg)
                    .transition(.move(edge: .top).combined(with: .opacity))
                    .zIndex(1)
            }

            // Simplified Loader Overlay using isLoading state
            if observableViewModel.state.isLoading {
                CustomLoaderView()
            }
        }
        .onAppear {
            observableViewModel.onNavigateToHomeCallback = onNavigateToHome
        }
    }
}

import SwiftUI
import Shared

class ObservableSignupViewModel: ObservableObject {
    let viewModel: SignupViewModel
    @Published var state: SignupUiState
    @Published var toastMessage: String? = nil
    var onNavigateToHomeCallback: (() -> Void)? = nil

    init(viewModel: SignupViewModel = SignupViewModel()) {
        self.viewModel = viewModel
        self.state = viewModel.uiState.value as! SignupUiState
        
        viewModel.observeState { [weak self] newState in
            DispatchQueue.main.async {
                self?.state = newState
            }
        }

        viewModel.observeEvents { [weak self] event in
            DispatchQueue.main.async {
                if let showToast = event as? SignupUiEventShowToast {
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
                } else if event is SignupUiEventNavigateToHome {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 1.2) {
                        self?.onNavigateToHomeCallback?()
                    }
                }
            }
        }
    }
}

struct SignupView: View {
    let onNavigateToHome: () -> Void
    @Environment(\.colorScheme) var colorScheme
    @StateObject private var observableViewModel = ObservableSignupViewModel()

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
                Text("Sign Up")
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

                // Confirm Password Input
                SecureField(
                    "",
                    text: Binding(
                        get: { observableViewModel.state.confirmPassword },
                        set: { observableViewModel.viewModel.onConfirmPasswordChange(newValue: $0) }
                    ),
                    prompt: Text("Confirm Password").foregroundColor(.gray)
                )
                .padding()
                .background(inputBg)
                .foregroundColor(inputFg)
                .cornerRadius(12)

                Spacer()

                // Sign Up Button
                Button(action: {
                    observableViewModel.viewModel.register()
                }) {
                    Text("Sign Up")
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

import SwiftUI

struct CustomLoaderView: View {
    @State private var isAnimating = false

    var body: some View {
        ZStack {
            // Full-screen overlay with 20% opacity black background
            Color.black.opacity(0.2)
                .ignoresSafeArea()

            VStack {
                Circle()
                    .stroke(Color.gray.opacity(0.2), lineWidth: 4)
                    .frame(width: 48, height: 48)
                    .overlay(
                        Circle()
                            .trim(from: 0.0, to: 0.7)
                            .stroke(
                                AngularGradient(
                                    gradient: Gradient(colors: [.blue, .cyan, .green, .blue]),
                                    center: .center
                                ),
                                style: StrokeStyle(lineWidth: 4, lineCap: .round)
                            )
                            .rotationEffect(Angle(degrees: isAnimating ? 360 : 0))
                            .onAppear {
                                DispatchQueue.main.async {
                                    withAnimation(
                                        Animation.linear(duration: 1.2)
                                            .repeatForever(autoreverses: false)
                                    ) {
                                        isAnimating = true
                                    }
                                }
                            }
                    )
                    .padding(.top, 40)
                Spacer()
            }
        }
    }
}

struct CustomLoaderView_Previews: PreviewProvider {
    static var previews: some View {
        CustomLoaderView()
            .preferredColorScheme(.dark)
    }
}

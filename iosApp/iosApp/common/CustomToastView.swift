import SwiftUI

struct CustomToastView: View {
    let message: String

    var body: some View {
        VStack {
            HStack(spacing: 8) {
                Text(message)
                    .font(.system(size: 15, weight: .medium))
                    .foregroundColor(.white)
                Spacer()
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 14)
            .background(Color(red: 50.0/255.0, green: 50.0/255.0, blue: 50.0/255.0))
            .cornerRadius(12)
            .shadow(radius: 4)
            .padding(.horizontal, 16)
            .padding(.top, 24)

            Spacer()
        }
    }
}

struct CustomToastView_Previews: PreviewProvider {
    static var previews: some View {
        CustomToastView(message: "Success Alert Message")
    }
}

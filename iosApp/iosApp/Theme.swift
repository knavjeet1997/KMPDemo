import SwiftUI

struct AppColors {
    // Light Mode Palette
    static let primaryBlueLight = Color(red: 0.10, green: 0.46, blue: 0.82) // #1976D2
    static let backgroundLight = Color(red: 0.98, green: 0.98, blue: 0.99) // #F9FBFC
    static let cardBackgroundLight = Color.white
    static let textLight = Color(red: 0.10, green: 0.11, blue: 0.12)
    static let textSecondaryLight = Color.gray
    
    // Dark Mode Palette
    static let primaryBlueDark = Color(red: 0.56, green: 0.79, blue: 0.98) // #90CAF9
    static let backgroundDark = Color(red: 0.07, green: 0.07, blue: 0.07) // #121212
    static let cardBackgroundDark = Color(red: 0.12, green: 0.12, blue: 0.12) // #1E1E1E
    static let textDark = Color(red: 0.89, green: 0.95, blue: 0.99)
    static let textSecondaryDark = Color.gray
    
    // Helper methods returning dynamic colors based on active ColorScheme
    static func primaryColor(for scheme: ColorScheme) -> Color {
        scheme == .dark ? primaryBlueDark : primaryBlueLight
    }
    
    static func backgroundColor(for scheme: ColorScheme) -> Color {
        scheme == .dark ? backgroundDark : backgroundLight
    }
    
    static func cardBackgroundColor(for scheme: ColorScheme) -> Color {
        scheme == .dark ? cardBackgroundDark : cardBackgroundLight
    }
    
    static func textColor(for scheme: ColorScheme) -> Color {
        scheme == .dark ? textDark : textLight
    }
    
    static func secondaryTextColor(for scheme: ColorScheme) -> Color {
        scheme == .dark ? textSecondaryDark : textSecondaryLight
    }
}

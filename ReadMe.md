# Spendie

Spendie is an intuitive expense-tracking application designed to help users monitor their spending
across different categories. Users can add, edit, and delete their expenses effortlessly. The app
also allows users to set individual budgets for categories and provides alerts based on usage.
Spendie offers insightful visualizations such as pie charts and progress indicators to help users
better understand their spending patterns. Additionally, the app includes a feature for currency
conversion from INR to USD.

## Features

- Add, edit, and delete expenses.
- Set and manage budgets for different categories.
- Visualize spending with pie charts and progress indicators.
- Real-time currency conversion from INR to USD.

## Technical Details

- **UI Framework:** Jetpack Compose
- **Architecture:** Clean Architecture with MVI Design Pattern
- **State Management:** Orbit Library
- **Dependency Injection:** Hilt
- **Charting Library:** Compose Charts
- **API Calls:** Retrofit
- **Forex Conversion API:** Vatcomply API
- **Minimum Android API Level:** 24

## API Configuration

Spendie fetches forex conversion rates using the [Vatcomply API](https://www.vatcomply.com/). Ensure
you have an active internet connection for accurate conversion rates.

## Contribution

Contributions are welcome! Feel free to submit issues or pull requests to improve the app.

## License

This project is licensed under the MIT License.

---


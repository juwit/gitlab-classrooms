/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["../resources/templates/**/*.{html,js}"], // it will be explained later
    theme: {
        extend: {},
        colors: {
            'primary': {
                40: "#1B275F",
                60: "#283A8F",
                80: "#364EBE",
                100: "#4361EE",
                120: "#6981F1",
                140: "#8EA0F5",
                160: "#B4C0F8",
                180: "#D9DFFC",
            },
            "dark": {
                1: "#111928",
                2: "#121B26",
                3: "#1F2A37",
                4: "#374151",
                5: "#4B5563",
                6: "#6B7280",
                7: "#9CA3AF",
                8: "#D1D5DB",
                9: "#E5E7EB",
                10: "#F1F2F3",
            },
            "white": "#FFFFFF",
            "red": {
                DEFAULT: "#F23030",
                "dark": "#E10E0E",
                "light": "#F56060",
                "light-20": "#F23030"
            },
            "teal": {
                DEFAULT: "#0B76B7",
                "dark": "#1F587A",
                "light": "#35ABF3",
                "light-20": "#0B76B7",
            },
            "orange": {
                DEFAULT: "#FBBF24",
                "dark": "#D8A00F",
                "light": "#F9D67B",
                "light-20": "#FBBF24",
            },
            "green": {
                DEFAULT: "#22AD5C",
                "dark": "#1A8245",
                "light": "#82E6AC",
                "light-20": "#22AD5C"
            }

        }
    },
    plugins: [
        require('@tailwindcss/forms'),
    ],
}
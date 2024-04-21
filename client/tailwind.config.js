/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      boxShadow: {
        cardShadow: "8px 8px 16px 0px #3434341A",
      },
      colors: {
        "blue-custom-100": "#FEFEFE",
        "blue-custom-200": "#F2F4FE",
        "blue-custom-300": "#E0E5FB",
        "blue-custom-400": "#D1D8F9",
        "blue-custom-500": "#A3B1F4",
        "blue-custom-700": "#8597F0",
        "blue-custom-900": "#667DEC",
        "blue-custom-1000": "#5366C4",
        "blue-custom-1100": "#3F4F9D",

        "violet-custom-400": "#E3DAFF",
        "violet-custom-500": "#D5C7FF",
        "violet-custom-700": "#C7B5FF",
        "violet-custom-900": "#B9A2FF",
        "violet-custom-1000": "#9A82E4",

        "black-custom-500": "#727272",
        "black-custom-700": "#606060",
        "black-custom-900": "#434343",
        "black-custom-1000": "#252525",
        "black-custom-1100": "#1E1E1E",
      },

      backgroundColor: {
        "bg-custom-gradient":
          "linear-gradient(204deg, rgba(63,100,225,1) 0%, rgba(137,143,245,1) 35%, rgba(177,140,254,1) 100%)",
      },
      outlineColor: {
        "grey-focus": "rgba(204, 196, 193, 1)",
      },
    },
  },
  plugins: [],
};

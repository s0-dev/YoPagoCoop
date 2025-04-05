/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{astro,svelte}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        background: 'var(--color-background)',
        text: 'var(--color-text)',

        primary: {
          DEFAULT: 'var(--color-primary)',
          darker: 'var(--color-primary-darker)',
          lighter: 'var(--color-primary-lighter)',
        },

        secondary: {
          DEFAULT: 'var(--color-secundary)',
          darker: 'var(--color-secundary-darker)',
          lighter: 'var(--color-secundary-lighter)',
        },

        accent: {
          DEFAULT: 'var(--color-accent)',
          darker: 'var(--color-accent-darker)',
          lighter: 'var(--color-accent-lighter)',
        },

        success: 'var(--color-success)',
        error: 'var(--color-error)',
        warning: 'var(--color-warning)',
        info: 'var(--color-info)',
        
      },
      // planteado mobile-first
      screens: {
        md: '768px',
        lg: '1024px',
        xl: '1280px',
      },
      fontFamily: {
        sans: ['var(--font-sans)'],
        serif: ['var(--font-serif)'],
        mono: ['var(--font-mono)'],
      },
    }
  }
}
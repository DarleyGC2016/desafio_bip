import { configDefaults, defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    globals: true,
    environment: 'jsdom',
    coverage: {
      provider: 'v8',
      reporter: ['text', 'html'],
      include: ['src/app/**/*.ts'],
      exclude: ['**/*.spec.ts', 'src/main.ts'],
      reportOnFailure: true,
    },
    exclude: [...configDefaults.exclude, 'src/main.ts'],
  },
});
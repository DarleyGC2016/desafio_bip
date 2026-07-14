import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    globals: true,
    environment: 'jsdom',      
    coverage: {
      provider: 'v8',
      reporter: ['text', 'html', 'clover', 'json'],
      include: ['src/app/**/*.ts'],
      exclude: [
        'src/app/app.config.ts',
        'src/app/**/*.spec.ts', 
        'src/main.ts',
        'src/app/shared/models/*.ts'
      ],
      enabled: true,
      reportOnFailure: true,
      thresholds: {
        autoUpdate: true,          
      },
    }
  },
});
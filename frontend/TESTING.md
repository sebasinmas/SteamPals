# 🧪 SteamPals Testing Documentation

## 📋 Tabla de Contenidos

1. [Configuración de Testing](#configuración-de-testing)
2. [Tipos de Tests](#tipos-de-tests)
3. [Estructura de Tests](#estructura-de-tests)
4. [Comandos de Testing](#comandos-de-testing)
5. [Cobertura de Tests](#cobertura-de-tests)
6. [Mejores Prácticas](#mejores-prácticas)
7. [Mocking Guidelines](#mocking-guidelines)

## 🔧 Configuración de Testing

### Stack de Testing

- **Vitest**: Test runner moderno y rápido
- **React Testing Library**: Testing de componentes React
- **jsdom**: Entorno DOM para tests
- **jest-dom**: Matchers adicionales para DOM testing

### Archivos de Configuración

- `vitest.config.ts`: Configuración principal de Vitest
- `client/test-setup.ts`: Setup global para tests
- `package.json`: Scripts de testing

## 🧪 Tipos de Tests

### 1. Unit Tests

Testean funciones y componentes individuales de forma aislada.

**Ubicación**: `client/**/__tests__/*.test.tsx`

**Ejemplo**:

```typescript
describe('Button Component', () => {
  it('should render with correct text', () => {
    render(<Button>Click me</Button>);
    expect(screen.getByText('Click me')).toBeInTheDocument();
  });
});
```

### 2. Integration Tests

Testean la interacción entre múltiples componentes.

**Ejemplo**:

```typescript
describe('Home Integration', () => {
  it('should navigate to profile when button clicked', () => {
    render(<Home />);
    fireEvent.click(screen.getByText('Mi Perfil'));
    expect(mockNavigate).toHaveBeenCalledWith('/profile');
  });
});
```

### 3. Component Tests

Testean el comportamiento completo de componentes.

**Ejemplo**:

```typescript
describe('Chat Component', () => {
  it('should send message and clear input', async () => {
    render(<Chat />);
    const input = screen.getByPlaceholderText('Escribe un mensaje...');

    fireEvent.change(input, { target: { value: 'Hello' } });
    fireEvent.click(screen.getByRole('button', { name: /Send/ }));

    await waitFor(() => {
      expect(screen.getByText('Hello')).toBeInTheDocument();
    });
    expect(input).toHaveValue('');
  });
});
```

## 📁 Estructura de Tests

```
client/
├── pages/
│   └── __tests__/
│       ├── Home.test.tsx      # Tests para Home component
│       ├── Chat.test.tsx      # Tests para Chat component
│       ├── Profile.test.tsx   # Tests para Profile component
│       └── Admin.test.tsx     # Tests para Admin component
├── lib/
│   └── __tests__/
│       └── utils.test.ts      # Tests para utilidades
├── components/ui/
│   └── __tests__/
│       └── *.test.tsx         # Tests para componentes UI
└── test-setup.ts              # Configuración global
```

## 🚀 Comandos de Testing

### Comandos Básicos

```bash
# Ejecutar todos los tests
npm test

# Ejecutar tests en modo watch
npm run test:watch

# Ejecutar tests con cobertura
npm run test:coverage

# Ejecutar tests específicos
npm test Home.test.tsx

# Ejecutar tests con reporte detallado
npm run test:verbose
```

### Comandos de Desarrollo

```bash
# Ejecutar tests de un componente específico
npx vitest run Home.test.tsx

# Ejecutar tests en modo interactivo
npx vitest --ui

# Ejecutar tests con debug
npx vitest --inspect-brk
```

## 📊 Cobertura de Tests

### Objetivos de Cobertura

- **Statements**: > 80%
- **Branches**: > 75%
- **Functions**: > 80%
- **Lines**: > 80%

### Configuración de Cobertura

```typescript
// vitest.config.ts
export default defineConfig({
  test: {
    coverage: {
      provider: "v8",
      reporter: ["text", "json", "html"],
      thresholds: {
        global: {
          statements: 80,
          branches: 75,
          functions: 80,
          lines: 80,
        },
      },
    },
  },
});
```

### Reporte de Cobertura

```bash
# Generar reporte HTML
npm run test:coverage

# Ver reporte en navegador
open coverage/index.html
```

## 📋 Tests por Componente

### Home Component Tests

- ✅ Rendering del header y navegación
- ✅ Sistema de matching (swipe left/right)
- ✅ Navegación a otras páginas
- ✅ Creación de chats
- ✅ Gestión de matches
- ✅ Estados de error y loading

### Chat Component Tests

- ✅ Selección de chats
- ✅ Envío de mensajes
- ✅ Búsqueda de chats
- ✅ Indicadores online/offline
- ✅ Funcionalidades de grupo
- ✅ Navegación y controles

### Profile Component Tests

- ✅ Modo de edición
- ✅ Actualización de datos
- ✅ Validación de formularios
- ✅ Conexión Steam
- ✅ Estadísticas gaming
- ✅ Navegación

### Admin Component Tests

- ✅ Búsqueda de usuarios
- ✅ Sistema de baneos
- ✅ Lista de usuarios baneados
- ✅ Estadísticas de moderación
- ✅ Validación de formularios
- ✅ Manejo de errores

### Utils Tests

- ✅ Función `cn()` para classes
- ✅ Combinación de clases
- ✅ Conditional styling
- ✅ Tailwind merging
- ✅ Edge cases

## 🎯 Mejores Prácticas

### 1. Naming Conventions

```typescript
// ✅ Buenos nombres de tests
describe("Home Component", () => {
  it("should render navigation buttons", () => {});
  it("should handle swipe right action", () => {});
  it("should navigate to profile page", () => {});
});

// ❌ Malos nombres de tests
describe("Home", () => {
  it("works", () => {});
  it("test navigation", () => {});
});
```

### 2. Test Organization

```typescript
describe("Component Name", () => {
  describe("Rendering", () => {
    // Tests de renderizado
  });

  describe("User Interactions", () => {
    // Tests de interacciones
  });

  describe("Error Handling", () => {
    // Tests de manejo de errores
  });

  describe("Accessibility", () => {
    // Tests de accesibilidad
  });
});
```

### 3. Assertions

```typescript
// ✅ Assertions específicas
expect(screen.getByText("SteamPals")).toBeInTheDocument();
expect(mockNavigate).toHaveBeenCalledWith("/profile");
expect(input).toHaveValue("");

// ❌ Assertions genéricas
expect(component).toBeTruthy();
expect(result).toBeDefined();
```

### 4. Async Testing

```typescript
// ✅ Correcto manejo de async
it("should send message", async () => {
  fireEvent.click(sendButton);

  await waitFor(() => {
    expect(screen.getByText("Message sent")).toBeInTheDocument();
  });
});

// ❌ Incorrecto manejo de async
it("should send message", () => {
  fireEvent.click(sendButton);
  expect(screen.getByText("Message sent")).toBeInTheDocument(); // Puede fallar
});
```

## 🎭 Mocking Guidelines

### 1. React Router

```typescript
const mockNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});
```

### 2. External Libraries

```typescript
// Mock Framer Motion
vi.mock('framer-motion', () => ({
  motion: {
    div: ({ children, ...props }: any) => <div {...props}>{children}</div>,
  },
  useMotionValue: () => ({ set: vi.fn() }),
  useTransform: () => 0,
}));
```

### 3. Browser APIs

```typescript
// Mock window methods
const mockConfirm = vi.fn();
vi.stubGlobal("confirm", mockConfirm);

// Mock localStorage
const mockStorage = {
  getItem: vi.fn(),
  setItem: vi.fn(),
  removeItem: vi.fn(),
};
vi.stubGlobal("localStorage", mockStorage);
```

### 4. API Calls

```typescript
// Mock fetch
global.fetch = vi.fn(() =>
  Promise.resolve({
    ok: true,
    json: () => Promise.resolve({ data: "mocked" }),
  }),
) as any;
```

## 🔍 Debugging Tests

### 1. Console Debugging

```typescript
it('should debug test', () => {
  render(<Component />);

  // Ver DOM actual
  screen.debug();

  // Ver elemento específico
  screen.debug(screen.getByTestId('element'));
});
```

### 2. Queries Debugging

```typescript
// Ver qué elementos están disponibles
screen.logTestingPlaygroundURL();

// Buscar elementos por texto parcial
screen.getByText(/partial text/i);

// Usar getBy vs queryBy vs findBy
const element = screen.queryByText("Might not exist"); // No falla si no existe
const element2 = await screen.findByText("Async element"); // Espera por elemento
```

### 3. Test Environment

```typescript
// Ver variables de entorno en tests
console.log("Test environment:", process.env.NODE_ENV);
console.log("Test mode:", import.meta.env.MODE);
```

## 📈 Métricas y Reporting

### Test Results

```bash
# Ver resultados detallados
npm run test -- --reporter=verbose

# Generar reporte JSON
npm run test -- --reporter=json --outputFile=test-results.json

# Generar reporte JUnit (para CI)
npm run test -- --reporter=junit --outputFile=junit.xml
```

### Performance Testing

```typescript
it('should render quickly', () => {
  const start = performance.now();

  render(<ExpensiveComponent />);

  const end = performance.now();
  const duration = end - start;

  expect(duration).toBeLessThan(100); // menos de 100ms
});
```

## 🚀 CI/CD Integration

### GitHub Actions

```yaml
# .github/workflows/test.yml
name: Tests
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-node@v3
        with:
          node-version: "18"
      - run: npm ci
      - run: npm run test:coverage
      - uses: codecov/codecov-action@v3
```

### Pre-commit Hooks

```json
// package.json
{
  "lint-staged": {
    "*.{ts,tsx}": ["npm run test -- --related --run", "npm run lint --fix"]
  }
}
```

## 📚 Recursos Adicionales

### Documentación

- [Vitest Documentation](https://vitest.dev/)
- [React Testing Library](https://testing-library.com/docs/react-testing-library/intro/)
- [Jest DOM Matchers](https://github.com/testing-library/jest-dom)

### Ejemplos

- [Testing Playground](https://testing-playground.com/)
- [React Testing Examples](https://react-testing-examples.com/)

---

_Última actualización: Enero 2024_

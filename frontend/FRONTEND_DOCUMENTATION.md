# 🎮 SteamPals Frontend Documentation

## 📋 Tabla de Contenidos

1. [Arquitectura General](#arquitectura-general)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Páginas y Componentes](#páginas-y-componentes)
5. [Sistema de Routing](#sistema-de-routing)
6. [Gestión de Estado](#gestión-de-estado)
7. [Sistema de Temas](#sistema-de-temas)
8. [Componentes UI](#componentes-ui)
9. [API de Funciones](#api-de-funciones)
10. [Flujos de Usuario](#flujos-de-usuario)

## 🏗️ Arquitectura General

SteamPals es una Single Page Application (SPA) desarrollada con React que permite a gamers conectar y formar equipos para jugar juntos. La aplicación está construida con una arquitectura modular y componente-based.

### Principios de Diseño

- **Componentización**: Cada funcionalidad está dividida en componentes reutilizables
- **Responsive Design**: Optimizada para desktop y móvil
- **Gaming-First**: UI diseñada específicamente para la experiencia gaming
- **Modularidad**: Código organizado en módulos independientes
- **Escalabilidad**: Estructura preparada para crecimiento futuro

## 🛠️ Tecnologías Utilizadas

### Core

- **React 18**: Biblioteca principal para la UI
- **TypeScript**: Tipado estático
- **Vite**: Build tool y dev server
- **React Router 6**: Routing SPA

### UI & Styling

- **TailwindCSS 3**: Framework de utilidades CSS
- **Radix UI**: Componentes UI accesibles
- **Lucide React**: Iconografía
- **Framer Motion**: Animaciones
- **shadcn/ui**: Sistema de componentes pre-construidos

### Utilidades

- **clsx**: Conditional classes
- **tailwind-merge**: Merge TailwindCSS classes
- **date-fns**: Manipulación de fechas

## 📁 Estructura del Proyecto

```
client/
├── pages/           # Páginas principales de la aplicación
│   ├── Index.tsx    # Landing page / Registro
│   ├── Login.tsx    # Página de login
│   ├── Home.tsx     # Home con sistema de matching
│   ├─�� Profile.tsx  # Perfil de usuario
│   ├── Settings.tsx # Configuración
│   ├── Chat.tsx     # Sistema de mensajería
│   ├── Admin.tsx    # Panel de administración
│   └── NotFound.tsx # Página 404
├── components/
│   └── ui/          # Componentes UI reutilizables
│       ├── button.tsx
│       ├── card.tsx
│       ├── input.tsx
│       ├── badge.tsx
│       └── ... (40+ componentes)
├── hooks/           # Custom React hooks
│   ├── use-mobile.tsx
│   └── use-toast.ts
├── lib/             # Utilidades y helpers
│   ├── utils.ts
│   └── utils.spec.ts
├── App.tsx          # Configuración de rutas
└── global.css       # Estilos globales y variables CSS
```

## 📱 Páginas y Componentes

### 1. **Index.tsx** - Landing Page / Registro

**Propósito**: Página principal para registro de nuevos usuarios

**Características**:

- Hero section con call-to-action
- Formulario de registro
- Información sobre la plataforma
- Features showcase
- Navegación a login

**Estado Local**:

```typescript
const [username, setUsername] = useState("");
const [password, setPassword] = useState("");
```

**Métodos principales**:

- `handleRegister()`: Procesa el registro de usuario
- `handleLogin()`: Navega a página de login

### 2. **Login.tsx** - Autenticación

**Propósito**: Autenticación de usuarios existentes

**Características**:

- Formulario de login
- Validación de credenciales
- Redirección automática al home
- Link de vuelta al registro

**Estado Local**:

```typescript
const [username, setUsername] = useState("");
const [password, setPassword] = useState("");
```

**Métodos principales**:

- `handleLogin()`: Autentica y redirige a /home

### 3. **Home.tsx** - Sistema de Matching

**Propósito**: Core de la aplicación - sistema de matching tipo Tinder para gamers

**Características**:

- Stack de cartas con perfiles de gamers
- Sistema de swipe (drag & drop)
- Botones de aceptar/rechazar
- Gestión de matches
- Navegación a otras secciones

**Estado Local**:

```typescript
const [profiles, setProfiles] = useState(SAMPLE_PROFILES);
const [currentIndex, setCurrentIndex] = useState(0);
const [matches, setMatches] = useState<GamerProfile[]>([]);
const [showMatches, setShowMatches] = useState(false);
const [showChat, setShowChat] = useState(false);
const [activeChats, setActiveChats] = useState([]);
```

**Métodos principales**:

- `handleSwipe(direction)`: Procesa swipe izquierda/derecha
- `createChat(pal)`: Crea nuevo chat con un match
- `resetCards()`: Reinicia el stack de cartas

**Componente SwipeCard**:

- Drag & drop con Framer Motion
- Animaciones de rotación y opacity
- Gestión de gestos táctiles

### 4. **Profile.tsx** - Perfil de Usuario

**Propósito**: Gestión del perfil personal del usuario

**Características**:

- Información personal editable
- Estadísticas gaming
- Conexión con Steam
- Edición in-place

**Estado Local**:

```typescript
const [isEditing, setIsEditing] = useState(false);
const [profile, setProfile] = useState({
  username,
  bio,
  location,
  age,
  favoriteGames,
  level,
  achievements,
  hoursPlayed,
  memberSince,
  status,
});
```

**Métodos principales**:

- `handleSave()`: Guarda cambios del perfil
- `handleCancel()`: Cancela edición

### 5. **Settings.tsx** - Configuración

**Propósito**: Configuración esencial de la cuenta

**Características**:

- Cambio de email
- Cambio de contraseña
- Conexión/desconexión Steam
- Eliminación de cuenta

**Estado Local**:

```typescript
const [email, setEmail] = useState("");
const [currentPassword, setCurrentPassword] = useState("");
const [newPassword, setNewPassword] = useState("");
const [confirmPassword, setConfirmPassword] = useState("");
const [steamConnected, setSteamConnected] = useState(true);
```

**Métodos principales**:

- `updatePassword()`: Cambia contraseña
- `connectSteam()`: Conecta/desconecta Steam
- `deleteAccount()`: Elimina cuenta permanentemente

### 6. **Chat.tsx** - Sistema de Mensajería

**Propósito**: Chat en tiempo real entre usuarios

**Características**:

- Lista de chats activos
- Interfaz de mensajería
- Búsqueda de chats
- Indicadores online/offline
- Envío de mensajes

**Estado Local**:

```typescript
const [selectedChat, setSelectedChat] = useState<string | null>("chat-1");
const [message, setMessage] = useState("");
const [searchTerm, setSearchTerm] = useState("");
const [allMessages, setAllMessages] = useState(initialMessages);
```

**Métodos principales**:

- `sendMessage()`: Envía nuevo mensaje
- `scrollToBottom()`: Auto-scroll a último mensaje

### 7. **Admin.tsx** - Panel de Administración

**Propósito**: Herramientas de moderación para administradores

**Características**:

- Búsqueda de usuarios
- Sistema de baneos
- Lista de usuarios baneados
- Estadísticas de moderación

**Estado Local**:

```typescript
const [searchUsername, setSearchUsername] = useState("");
const [banReason, setBanReason] = useState("");
const [banDuration, setBanDuration] = useState("");
const [foundUser, setFoundUser] = useState(null);
const [bannedUsers, setBannedUsers] = useState([]);
```

**Métodos principales**:

- `searchUser()`: Busca usuario por nombre
- `banUser()`: Banea usuario con razón y duración
- `unbanUser()`: Quita ban a usuario

## 🔄 Sistema de Routing

La aplicación utiliza React Router 6 configurado en `App.tsx`:

```typescript
<Routes>
  <Route path="/" element={<Index />} />           // Landing/Registro
  <Route path="/login" element={<Login />} />      // Autenticación
  <Route path="/home" element={<Home />} />        // Matching principal
  <Route path="/profile" element={<Profile />} />  // Perfil usuario
  <Route path="/settings" element={<Settings />} /> // Configuración
  <Route path="/chat" element={<Chat />} />        // Mensajería
  <Route path="/admin" element={<Admin />} />      // Panel admin
  <Route path="*" element={<NotFound />} />        // 404
</Routes>
```

### Flujo de Navegación

1. **/** → Registro → **/login** → **/home**
2. **Desde /home** → **/profile**, **/settings**, **/chat**
3. **Admin** → **/admin** (acceso especial)

## 🎨 Sistema de Temas

### Variables CSS (global.css)

El tema está definido con CSS custom properties en formato HSL:

```css
:root {
  --background: 210 30% 8%; /* Fondo principal oscuro */
  --foreground: 210 40% 98%; /* Texto principal claro */
  --primary: 210 100% 56%; /* Azul gaming principal */
  --accent: 258 100% 70%; /* Púrpura accent */
  --card: 210 30% 10%; /* Fondo de cartas */
  --border: 210 30% 18%; /* Bordes */
  --muted: 210 30% 12%; /* Elementos secundarios */
}
```

### Paleta de Colores Gaming

- **Primario**: Azul Steam (#1F8FFF)
- **Accent**: Púrpura gaming (#9466FF)
- **Fondo**: Oscuro gaming (#0E141B)
- **Éxito**: Verde (#10B981)
- **Error**: Rojo (#EF4444)

## 🧩 Componentes UI Principales

### Button

```typescript
interface ButtonProps {
  variant?:
    | "default"
    | "destructive"
    | "outline"
    | "secondary"
    | "ghost"
    | "link";
  size?: "default" | "sm" | "lg" | "icon";
  className?: string;
}
```

### Card

```typescript
interface CardProps {
  className?: string;
  children: React.ReactNode;
}
```

### Input

```typescript
interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  className?: string;
}
```

### Badge

```typescript
interface BadgeProps {
  variant?: "default" | "secondary" | "destructive" | "outline";
  className?: string;
}
```

## 🔄 Gestión de Estado

### Estado por Componente

Cada página maneja su propio estado local con `useState`:

- **Home**: Perfiles, matches, chats activos
- **Chat**: Mensajes, chat seleccionado, búsqueda
- **Profile**: Datos del perfil, modo edición
- **Admin**: Usuarios baneados, búsquedas

### Comunicación entre Componentes

- **React Router**: Navegación entre páginas
- **Props**: Paso de datos a componentes hijos
- **Callbacks**: Eventos de componentes hijos a padres

## 🎮 Funcionalidades Gaming Específicas

### Sistema de Matching

- **Algoritmo tipo Tinder** para encontrar compañeros de juego
- **Gestos táctiles** para móvil y desktop
- **Animaciones fluidas** con Framer Motion
- **Filtrado por juegos** y estilo de juego

### Perfiles Gaming

- **Integración Steam** (simulada)
- **Estadísticas gaming**: nivel, logros, horas
- **Juegos favoritos** y preferencias
- **Estados online/offline**

### Chat Gaming

- **Mensajería instantánea** entre matches
- **Creación de grupos** para formar equipos
- **Indicadores de actividad** gaming

## 📱 Responsive Design

### Breakpoints

- **Mobile**: < 640px
- **Tablet**: 640px - 1024px
- **Desktop**: > 1024px

### Adaptaciones Móvil

- **Navegación simplificada** (solo iconos)
- **Cards stack optimizado** para touch
- **Chat responsive** con sidebar colapsable
- **Formularios optimizados** para móvil

## 🔧 Utilidades y Helpers

### `cn()` Function (lib/utils.ts)

```typescript
import { clsx } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
```

Combina classes de TailwindCSS y permite conditional styling.

### Custom Hooks

- **use-mobile.tsx**: Detecta si es dispositivo móvil
- **use-toast.ts**: Sistema de notificaciones

## 🚀 Performance y Optimizaciones

### Code Splitting

- **Lazy loading** de páginas no críticas
- **Dynamic imports** para componentes pesados

### Optimizaciones UI

- **Virtualization** para listas largas (chat)
- **Debouncing** en búsquedas
- **Memoization** de componentes pesados

### Animaciones Optimizadas

- **Framer Motion** para animaciones GPU-accelerated
- **Transform-based** animations para mejor performance
- **Reduced motion** respeta preferencias de accesibilidad

## 🔒 Seguridad y Validación

### Validación Client-Side

- **TypeScript** para type safety
- **Form validation** en tiempo real
- **Input sanitization** para prevenir XSS

### Manejo de Errores

- **Error boundaries** para capturar errores React
- **Try-catch** en operaciones async
- **Fallbacks** para estados de error

## 📈 Analytics y Monitoreo

### Eventos Trackados

- **User registration** y login
- **Match success rate**
- **Chat engagement**
- **Feature usage** patterns

### Performance Metrics

- **Page load times**
- **Component render times**
- **User interaction** latency

## 🧪 Testing Strategy

### Unit Tests

- **Componentes individuales**
- **Funciones utilitarias**
- **Custom hooks**

### Integration Tests

- **Flujos completos** de usuario
- **Navegación entre páginas**
- **Interacciones complejas**

### E2E Tests

- **Registro y login**
- **Matching workflow**
- **Chat functionality**

---

## 📞 Contacto y Soporte

Para dudas sobre el frontend o contribuciones:

- **Documentación técnica**: Este archivo
- **Issues**: GitHub Issues
- **Contribuciones**: Pull Requests bienvenidos

---

_Última actualización: Julio 2025_
_Versión: 1.0.0_

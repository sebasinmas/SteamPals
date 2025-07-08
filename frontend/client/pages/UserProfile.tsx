import { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Gamepad2,
  ArrowLeft,
  MessageSquare,
  UserPlus,
  Monitor,
  Trophy,
  Clock,
  Star,
  MapPin,
  Calendar,
  Ban,
} from "lucide-react";

interface UserProfileData {
  username: string;
  bio: string;
  location: string;
  age: number;
  favoriteGames: string[];
  level: number;
  achievements: number;
  hoursPlayed: string;
  memberSince: string;
  status: string;
  avatar: string;
  steamConnected: boolean;
  isOnline: boolean;
}

// Mock profiles data - en una app real vendría de una API
const MOCK_PROFILES: { [key: string]: UserProfileData } = {
  ShadowHunter87: {
    username: "ShadowHunter87",
    bio: "Jugador competitivo de FPS y RPG. Busco squad para ranked!",
    location: "Madrid, España",
    age: 24,
    favoriteGames: ["CS2", "Valorant", "Elden Ring"],
    level: 87,
    achievements: 342,
    hoursPlayed: "2,450h",
    memberSince: "Enero 2023",
    status: "En línea",
    avatar: "🎮",
    steamConnected: true,
    isOnline: true,
  },
  StarGazer_22: {
    username: "StarGazer_22",
    bio: "MMO addict y streamer ocasional. Siempre lista para una aventura épica!",
    location: "Barcelona, España",
    age: 22,
    favoriteGames: ["FF XIV", "WoW", "Genshin Impact"],
    level: 95,
    achievements: 578,
    hoursPlayed: "3,120h",
    memberSince: "Noviembre 2022",
    status: "Desconectado",
    avatar: "🌟",
    steamConnected: true,
    isOnline: false,
  },
  ThunderStrike_Pro: {
    username: "ThunderStrike_Pro",
    bio: "Pro player retirado. Ahora juego por diversión y ayudo a nuevos jugadores!",
    location: "Valencia, España",
    age: 27,
    favoriteGames: ["League of Legends", "Rocket League", "Apex"],
    level: 120,
    achievements: 892,
    hoursPlayed: "5,670h",
    memberSince: "Marzo 2022",
    status: "Ausente",
    avatar: "⚡",
    steamConnected: true,
    isOnline: false,
  },
  LunarAce_21: {
    username: "LunarAce_21",
    bio: "Speedrunner y coleccionista de logros. ¿Quién se apunta a completar juegos al 100%?",
    location: "Sevilla, España",
    age: 21,
    favoriteGames: ["Hollow Knight", "Celeste", "Dark Souls"],
    level: 78,
    achievements: 1205,
    hoursPlayed: "1,890h",
    memberSince: "Junio 2023",
    status: "En línea",
    avatar: "🎯",
    steamConnected: true,
    isOnline: true,
  },
};

export default function UserProfile() {
  const { username } = useParams<{ username: string }>();
  const [profile, setProfile] = useState<UserProfileData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (username && MOCK_PROFILES[username]) {
      setProfile(MOCK_PROFILES[username]);
    } else {
      setProfile(null);
    }
    setLoading(false);
  }, [username]);

  const handleSendMessage = () => {
    console.log(`Enviar mensaje a ${profile?.username}`);
    // Crear chat y navegar a la página de chat
    window.location.href = "/chat";
  };

  const handleAddFriend = () => {
    console.log(`Agregar como amigo a ${profile?.username}`);
    // Aquí iría la lógica para agregar amigo
    alert(`Solicitud de amistad enviada a ${profile?.username}`);
  };

  const handleReport = () => {
    if (
      confirm(`¿Estás seguro de que quieres reportar a ${profile?.username}?`)
    ) {
      console.log(`Reportar usuario ${profile?.username}`);
      alert(`Usuario ${profile?.username} reportado correctamente`);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-background via-background to-card flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 rounded-full bg-gradient-to-br from-primary to-accent animate-pulse mx-auto mb-4"></div>
          <p className="text-muted-foreground">Cargando perfil...</p>
        </div>
      </div>
    );
  }

  if (!profile) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-background via-background to-card">
        {/* Header */}
        <header className="border-b border-border/50 backdrop-blur-sm bg-background/80 sticky top-0 z-50">
          <div className="container mx-auto px-4 py-4">
            <div className="flex items-center justify-between">
              <Link to="/home" className="flex items-center space-x-3">
                <div className="relative">
                  <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                    <Gamepad2 className="w-6 h-6 text-primary-foreground" />
                  </div>
                  <div className="absolute -top-1 -right-1 w-4 h-4 bg-accent rounded-full animate-pulse"></div>
                </div>
                <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                  SteamPals
                </h1>
              </Link>
              <Link to="/home">
                <Button variant="ghost" className="hover:bg-muted/50">
                  <ArrowLeft className="w-4 h-4 mr-2" />
                  Volver
                </Button>
              </Link>
            </div>
          </div>
        </header>

        <div className="flex items-center justify-center min-h-[calc(100vh-80px)]">
          <Card className="bg-card/80 backdrop-blur-sm border-border/50 text-center p-8">
            <CardContent>
              <div className="w-16 h-16 rounded-full bg-muted mx-auto mb-4 flex items-center justify-center">
                <Gamepad2 className="w-8 h-8 text-muted-foreground" />
              </div>
              <h2 className="text-2xl font-bold mb-2">Usuario no encontrado</h2>
              <p className="text-muted-foreground">
                El perfil que buscas no existe o no está disponible.
              </p>
            </CardContent>
          </Card>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-background via-background to-card">
      {/* Header */}
      <header className="border-b border-border/50 backdrop-blur-sm bg-background/80 sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <Link to="/home" className="flex items-center space-x-3">
              <div className="relative">
                <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                  <Gamepad2 className="w-6 h-6 text-primary-foreground" />
                </div>
                <div className="absolute -top-1 -right-1 w-4 h-4 bg-accent rounded-full animate-pulse"></div>
              </div>
              <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                SteamPals
              </h1>
            </Link>
            <Link to="/home">
              <Button variant="ghost" className="hover:bg-muted/50">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Volver
              </Button>
            </Link>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8 max-w-4xl">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Profile Card */}
          <div className="lg:col-span-1">
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardContent className="p-6 text-center">
                <div className="relative inline-block mb-4">
                  <div className="w-24 h-24 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-4xl mx-auto">
                    {profile.avatar}
                  </div>
                  <div
                    className={`absolute bottom-2 right-2 w-6 h-6 rounded-full border-2 border-background ${
                      profile.isOnline ? "bg-green-400" : "bg-gray-400"
                    }`}
                  ></div>
                </div>

                <h2 className="text-2xl font-bold mb-2">{profile.username}</h2>
                <div className="flex items-center justify-center text-muted-foreground mb-2">
                  <MapPin className="w-4 h-4 mr-1" />
                  {profile.location}
                </div>
                <Badge
                  variant={profile.isOnline ? "default" : "secondary"}
                  className="mb-4"
                >
                  {profile.status}
                </Badge>

                {/* Action Buttons */}
                <div className="space-y-3">
                  <Button
                    onClick={handleSendMessage}
                    className="w-full bg-gradient-to-r from-primary to-accent hover:from-primary/90 hover:to-accent/90"
                  >
                    <MessageSquare className="w-4 h-4 mr-2" />
                    Enviar Mensaje
                  </Button>

                  <Button
                    onClick={handleAddFriend}
                    variant="outline"
                    className="w-full"
                  >
                    <UserPlus className="w-4 h-4 mr-2" />
                    Agregar Amigo
                  </Button>

                  <Button
                    onClick={handleReport}
                    variant="outline"
                    className="w-full text-destructive border-destructive/50 hover:bg-destructive/10"
                  >
                    <Ban className="w-4 h-4 mr-2" />
                    Reportar Usuario
                  </Button>
                </div>

                {/* Steam Connection */}
                <div className="mt-6 p-4 bg-muted/20 rounded-lg">
                  <div className="flex items-center justify-center space-x-2">
                    <Monitor className="w-5 h-5 text-primary" />
                    <span className="font-medium">Steam</span>
                    {profile.steamConnected ? (
                      <Badge className="bg-green-600">Conectado</Badge>
                    ) : (
                      <Badge variant="destructive">Desconectado</Badge>
                    )}
                  </div>
                </div>

                {/* Stats */}
                <div className="grid grid-cols-2 gap-4 mt-6">
                  <div className="text-center">
                    <div className="text-2xl font-bold text-primary">
                      {profile.level}
                    </div>
                    <div className="text-xs text-muted-foreground">Nivel</div>
                  </div>
                  <div className="text-center">
                    <div className="text-2xl font-bold text-accent">
                      {profile.achievements}
                    </div>
                    <div className="text-xs text-muted-foreground">Logros</div>
                  </div>
                </div>

                <div className="mt-4 text-center">
                  <div className="flex items-center justify-center text-muted-foreground">
                    <Calendar className="w-4 h-4 mr-1" />
                    <span className="text-sm">
                      Miembro desde {profile.memberSince}
                    </span>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Profile Details */}
          <div className="lg:col-span-2 space-y-6">
            {/* Bio */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle>Biografía</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-muted-foreground">{profile.bio}</p>
              </CardContent>
            </Card>

            {/* Gaming Stats */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Trophy className="w-5 h-5 mr-2" />
                  Estadísticas Gaming
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                  <div className="text-center p-4 bg-muted/20 rounded-lg">
                    <Clock className="w-8 h-8 text-primary mx-auto mb-2" />
                    <div className="text-2xl font-bold">
                      {profile.hoursPlayed}
                    </div>
                    <div className="text-sm text-muted-foreground">
                      Horas jugadas
                    </div>
                  </div>
                  <div className="text-center p-4 bg-muted/20 rounded-lg">
                    <Trophy className="w-8 h-8 text-accent mx-auto mb-2" />
                    <div className="text-2xl font-bold">
                      {profile.achievements}
                    </div>
                    <div className="text-sm text-muted-foreground">
                      Logros obtenidos
                    </div>
                  </div>
                  <div className="text-center p-4 bg-muted/20 rounded-lg">
                    <Star className="w-8 h-8 text-primary mx-auto mb-2" />
                    <div className="text-2xl font-bold">{profile.level}</div>
                    <div className="text-sm text-muted-foreground">
                      Nivel actual
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Favorite Games */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle>Juegos Favoritos</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex flex-wrap gap-2">
                  {profile.favoriteGames.map((game) => (
                    <Badge key={game} variant="secondary" className="text-sm">
                      {game}
                    </Badge>
                  ))}
                </div>
              </CardContent>
            </Card>

            {/* Recent Activity */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle>Actividad Reciente</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3 text-sm text-muted-foreground">
                  <div className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-green-400 rounded-full"></div>
                    <span>Jugó Valorant hace 2 horas</span>
                  </div>
                  <div className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-blue-400 rounded-full"></div>
                    <span>Desbloqueó logro "Headshot Master" hace 1 día</span>
                  </div>
                  <div className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-purple-400 rounded-full"></div>
                    <span>Subió al nivel {profile.level} hace 3 días</span>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}

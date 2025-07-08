import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { motion, useMotionValue, useTransform, PanInfo } from "framer-motion";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Heart,
  X,
  Gamepad2,
  MapPin,
  Clock,
  Trophy,
  Star,
  Settings,
  MessageSquare,
  Users,
  User,
  UserCheck,
} from "lucide-react";

interface GamerProfile {
  id: number;
  name: string;
  age: number;
  location: string;
  avatar: string;
  bio: string;
  favoriteGames: string[];
  playStyle: string[];
  level: number;
  achievements: number;
  hoursPlayed: string;
}

const SAMPLE_PROFILES: GamerProfile[] = [
  {
    id: 1,
    name: "ShadowHunter87",
    age: 24,
    location: "Madrid, España",
    avatar: "🎮",
    bio: "Jugador competitivo de FPS y RPG. Busco squad para ranked!",
    favoriteGames: ["CS2", "Valorant", "Elden Ring"],
    playStyle: ["Competitivo", "Estratégico", "Team Player"],
    level: 87,
    achievements: 342,
    hoursPlayed: "2,450h",
  },
  {
    id: 2,
    name: "StarGazer_22",
    age: 22,
    location: "Barcelona, España",
    avatar: "🌟",
    bio: "MMO addict y streamer ocasional. Siempre lista para una aventura épica!",
    favoriteGames: ["FF XIV", "WoW", "Genshin Impact"],
    playStyle: ["Cooperativo", "PvE", "Casual"],
    level: 95,
    achievements: 578,
    hoursPlayed: "3,120h",
  },
  {
    id: 3,
    name: "ThunderStrike_Pro",
    age: 27,
    location: "Valencia, España",
    avatar: "⚡",
    bio: "Pro player retirado. Ahora juego por diversión y ayudo a nuevos jugadores!",
    favoriteGames: ["League of Legends", "Rocket League", "Apex"],
    playStyle: ["Mentor", "Competitivo", "Estratégico"],
    level: 120,
    achievements: 892,
    hoursPlayed: "5,670h",
  },
  {
    id: 4,
    name: "LunarAce_21",
    age: 21,
    location: "Sevilla, España",
    avatar: "🎯",
    bio: "Speedrunner y coleccionista de logros. ¿Quién se apunta a completar juegos al 100%?",
    favoriteGames: ["Hollow Knight", "Celeste", "Dark Souls"],
    playStyle: ["Speedrun", "Completionist", "Hardcore"],
    level: 78,
    achievements: 1205,
    hoursPlayed: "1,890h",
  },
];

export default function Home() {
  const navigate = useNavigate();
  const [profiles, setProfiles] = useState(SAMPLE_PROFILES);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [matches, setMatches] = useState<GamerProfile[]>([]);
  const [showMatches, setShowMatches] = useState(false);
  const [showChat, setShowChat] = useState(false);
  const [activeChats, setActiveChats] = useState<
    {
      id: string;
      name: string;
      avatar: string;
      lastMessage: string;
      timestamp: string;
      isGroup: boolean;
      participants?: string[];
    }[]
  >([]);

  // Cargar chats y matches persistentes al montar el componente
  useEffect(() => {
    const savedChats = localStorage.getItem("steamPalsChats");
    if (savedChats) {
      setActiveChats(JSON.parse(savedChats));
    }

    const savedMatches = localStorage.getItem("steamPalsMatches");
    if (savedMatches) {
      setMatches(JSON.parse(savedMatches));
    }
  }, []);

  const currentProfile = profiles[currentIndex];

  const handleSwipe = (direction: "left" | "right") => {
    if (direction === "right" && currentProfile) {
      setMatches((prev) => {
        // Evitar duplicados
        const existingMatch = prev.find(
          (match) => match.id === currentProfile.id,
        );
        if (existingMatch) {
          return prev; // No agregar si ya existe
        }

        const updatedMatches = [...prev, currentProfile];
        // Guardar matches en localStorage
        localStorage.setItem(
          "steamPalsMatches",
          JSON.stringify(updatedMatches),
        );
        return updatedMatches;
      });
    }

    setCurrentIndex((prev) => prev + 1);
  };

  const resetCards = () => {
    setCurrentIndex(0);
    setMatches([]);
    localStorage.removeItem("steamPalsMatches");
    localStorage.removeItem("steamPalsChats");
    setActiveChats([]);
  };

  const createChat = (pal: GamerProfile) => {
    alert(`🔧 Intentando crear chat con: ${pal.name}`);

    const newChat = {
      id: `chat-${Date.now()}`, // ID único basado en timestamp
      name: pal.name,
      avatar: pal.avatar,
      lastMessage: "¡Hola! ¿Quieres jugar juntos?",
      timestamp: "ahora",
      isGroup: false,
    };

    // Actualizar estado inmediatamente
    const updatedChats = [...activeChats, newChat];
    setActiveChats(updatedChats);

    // Guardar en localStorage
    localStorage.setItem("steamPalsChats", JSON.stringify(updatedChats));

    alert(
      `✅ Chat creado con ${pal.name}. Total chats: ${updatedChats.length}`,
    );

    // Navegar al chat después de un pequeño delay
    setTimeout(() => {
      navigate("/chat");
    }, 500);
  };

  if (!currentProfile) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-background via-background to-card flex items-center justify-center">
        <Card className="p-8 text-center bg-card/80 backdrop-blur-sm border-border/50">
          <CardContent className="space-y-4">
            <Trophy className="w-16 h-16 text-accent mx-auto" />
            <h2 className="text-2xl font-bold">¡No hay más perfiles!</h2>
            <p className="text-muted-foreground">
              Has revisado todos los gamers disponibles.
            </p>
            <p className="text-sm text-accent font-medium">
              Pals encontrados: {matches.length}
            </p>
            <Button
              variant="outline"
              onClick={() => setShowMatches(true)}
              disabled={matches.length === 0}
            >
              Ver Pals ({matches.length})
            </Button>
          </CardContent>
        </Card>
      </div>
    );
  }

  if (showMatches) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-background via-background to-card p-4">
        <div className="container mx-auto max-w-2xl">
          <div className="flex items-center justify-between mb-6">
            <h1 className="text-3xl font-bold">Tus Pals 🎮</h1>
            <Button variant="outline" onClick={() => setShowMatches(false)}>
              Volver
            </Button>
          </div>

          <div className="space-y-4">
            {matches.map((match) => (
              <Card
                key={`match-list-${match.id}`}
                className="bg-card/80 backdrop-blur-sm border-border/50"
              >
                <CardContent className="p-4">
                  <div className="flex items-center space-x-4">
                    <div className="w-16 h-16 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-2xl">
                      {match.avatar}
                    </div>
                    <div className="flex-1">
                      <h3 className="font-bold text-lg">{match.name}</h3>
                      <p className="text-muted-foreground">{match.bio}</p>
                      <div className="flex gap-2 mt-2">
                        {match.favoriteGames.slice(0, 2).map((game) => (
                          <Badge key={game} variant="secondary">
                            {game}
                          </Badge>
                        ))}
                      </div>
                    </div>
                    <Button
                      className="bg-accent hover:bg-accent/90"
                      onClick={() => createChat(match)}
                    >
                      <MessageSquare className="w-4 h-4 mr-2" />
                      Chat
                    </Button>
                  </div>
                </CardContent>
              </Card>
            ))}

            {matches.length === 0 && (
              <div className="text-center py-12">
                <Users className="w-16 h-16 text-muted-foreground mx-auto mb-4" />
                <p className="text-muted-foreground">Aún no tienes pals.</p>
                <p className="text-sm text-muted-foreground">
                  ¡Sigue deslizando para encontrar gamers!
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    );
  }

  if (showChat) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-background via-background to-card">
        {/* Header */}
        <header className="border-b border-border/50 backdrop-blur-sm bg-background/80 sticky top-0 z-50">
          <div className="container mx-auto px-4 py-4">
            <div className="flex items-center justify-between">
              <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                Chat Center ��
              </h1>
              <Button variant="outline" onClick={() => setShowChat(false)}>
                Volver
              </Button>
            </div>
          </div>
        </header>

        <div className="container mx-auto px-4 py-8 max-w-4xl">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Create Personal Chat */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <User className="w-5 h-5 text-primary" />
                  Chat Personal
                </CardTitle>
                <p className="text-muted-foreground">
                  Conversa directamente con uno de tus pals
                </p>
              </CardHeader>
              <CardContent className="space-y-4">
                {matches.length > 0 ? (
                  <>
                    <h4 className="font-medium text-sm">Selecciona un pal:</h4>
                    <div className="space-y-2">
                      {matches.map((pal) => (
                        <Button
                          key={`personal-${pal.id}`}
                          variant="outline"
                          className="w-full justify-start"
                          onClick={() => {
                            createChat(pal);
                            console.log(`Chat creado con ${pal.name}`);
                          }}
                        >
                          <div className="flex items-center space-x-3">
                            <span className="text-lg">{pal.avatar}</span>
                            <div className="text-left">
                              <div className="font-medium">{pal.name}</div>
                            </div>
                          </div>
                        </Button>
                      ))}
                    </div>
                  </>
                ) : (
                  <div className="text-center py-8">
                    <Users className="w-12 h-12 text-muted-foreground mx-auto mb-3" />
                    <p className="text-muted-foreground">
                      Necesitas pals para crear chats
                    </p>
                    <p className="text-sm text-muted-foreground">
                      ¡Ve a hacer match primero!
                    </p>
                  </div>
                )}
              </CardContent>
            </Card>

            {/* Create Group Chat */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <Users className="w-5 h-5 text-accent" />
                  Chat Grupal
                </CardTitle>
                <p className="text-muted-foreground">
                  Crea un grupo con múltiples pals para jugar juntos
                </p>
              </CardHeader>
              <CardContent className="space-y-4">
                {matches.length >= 2 ? (
                  <>
                    <div className="space-y-3">
                      <label className="text-sm font-medium">
                        Nombre del grupo:
                      </label>
                      <input
                        type="text"
                        placeholder="Ej: Squad Valorant, Raid WoW..."
                        className="w-full px-3 py-2 bg-input/50 border border-border/50 rounded-md focus:border-primary outline-none transition-colors"
                      />
                    </div>

                    <div className="space-y-2">
                      <label className="text-sm font-medium">
                        Selecciona pals (mín. 2):
                      </label>
                      {matches.map((pal) => (
                        <div
                          key={`group-${pal.id}`}
                          className="flex items-center space-x-3 p-2 rounded-lg hover:bg-muted/30"
                        >
                          <input
                            type="checkbox"
                            id={`group-${pal.id}`}
                            className="accent-primary"
                          />
                          <label
                            htmlFor={`group-${pal.id}`}
                            className="flex items-center space-x-3 flex-1 cursor-pointer"
                          >
                            <span className="text-lg">{pal.avatar}</span>
                            <div>
                              <div className="font-medium">{pal.name}</div>
                              <div className="text-xs text-muted-foreground">
                                {pal.favoriteGames.slice(0, 2).join(", ")}
                              </div>
                            </div>
                          </label>
                        </div>
                      ))}
                    </div>

                    <Button
                      className="w-full bg-gradient-to-r from-accent to-primary hover:from-accent/90 hover:to-primary/90"
                      onClick={() => {
                        const groupName =
                          (
                            document.querySelector(
                              'input[placeholder*="Ej: Squad"]',
                            ) as HTMLInputElement
                          )?.value || "Squad Gaming";
                        const selectedPals = matches.filter((_, index) => {
                          const checkbox = document.querySelector(
                            `#group-${matches[index].id}`,
                          ) as HTMLInputElement;
                          return checkbox?.checked;
                        });

                        if (selectedPals.length >= 2) {
                          const groupChat = {
                            id: `group-${Date.now()}`,
                            name: groupName,
                            avatar: "🏆",
                            lastMessage: "Grupo creado - ¡A jugar!",
                            timestamp: "ahora",
                            isGroup: true,
                            participants: selectedPals.map((p) => p.name),
                          };

                          setActiveChats((prev) => [...prev, groupChat]);

                          // Guardar en localStorage
                          const savedChats =
                            localStorage.getItem("steamPalsChats");
                          const existingChats = savedChats
                            ? JSON.parse(savedChats)
                            : [];
                          const updatedChats = [...existingChats, groupChat];
                          localStorage.setItem(
                            "steamPalsChats",
                            JSON.stringify(updatedChats),
                          );

                          navigate("/chat");
                        } else {
                          alert(
                            "Selecciona al menos 2 pals para crear un grupo",
                          );
                        }
                      }}
                    >
                      <Users className="w-4 h-4 mr-2" />
                      Crear Grupo
                    </Button>
                  </>
                ) : (
                  <div className="text-center py-8">
                    <Users className="w-12 h-12 text-muted-foreground mx-auto mb-3" />
                    <p className="text-muted-foreground">
                      Necesitas al menos 2 pals para crear un grupo
                    </p>
                    <p className="text-sm text-muted-foreground">
                      Tienes {matches.length} pals
                    </p>
                  </div>
                )}
              </CardContent>
            </Card>
          </div>

          {/* Active Chats */}
          {activeChats.length > 0 && (
            <div className="mt-8">
              <h2 className="text-xl font-bold mb-4">Chats Activos</h2>
              <div className="space-y-3">
                {activeChats.map((chat) => (
                  <Card
                    key={`active-chat-${chat.id}`}
                    className="bg-card/80 backdrop-blur-sm border-border/50 hover:bg-card/90 transition-colors cursor-pointer"
                    onClick={() => console.log(`Abrir chat: ${chat.name}`)}
                  >
                    <CardContent className="p-4">
                      <div className="flex items-center space-x-4">
                        <div className="relative">
                          {chat.isGroup ? (
                            <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                              <Users className="w-6 h-6 text-primary-foreground" />
                            </div>
                          ) : (
                            <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-xl">
                              {chat.avatar}
                            </div>
                          )}
                          <div className="absolute bottom-0 right-0 w-4 h-4 bg-green-400 rounded-full border-2 border-background"></div>
                        </div>

                        <div className="flex-1 min-w-0">
                          <div className="flex items-center justify-between">
                            <h3 className="font-medium truncate">
                              {chat.name}
                            </h3>
                            <span className="text-xs text-muted-foreground">
                              {chat.timestamp}
                            </span>
                          </div>
                          <p className="text-sm text-muted-foreground truncate mt-1">
                            {chat.lastMessage}
                          </p>
                          {chat.isGroup && chat.participants && (
                            <p className="text-xs text-muted-foreground mt-1">
                              {chat.participants.length} participantes
                            </p>
                          )}
                        </div>

                        <div className="flex flex-col items-end space-y-1">
                          <div className="w-3 h-3 bg-accent rounded-full"></div>
                          <Badge variant="secondary" className="text-xs">
                            {chat.isGroup ? "Grupo" : "Personal"}
                          </Badge>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            </div>
          )}

          {/* Sample Group Chat */}
          {matches.length >= 2 && activeChats.length === 0 && (
            <div className="mt-8">
              <h2 className="text-xl font-bold mb-4">Chats Sugeridos</h2>
              <Card className="bg-card/80 backdrop-blur-sm border-border/50 hover:bg-card/90 transition-colors cursor-pointer">
                <CardContent className="p-4">
                  <div className="flex items-center space-x-4">
                    <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                      <Users className="w-6 h-6 text-primary-foreground" />
                    </div>
                    <div className="flex-1">
                      <h3 className="font-medium">Squad Gaming</h3>
                      <p className="text-sm text-muted-foreground">
                        Crear grupo con tus pals para jugar
                      </p>
                    </div>
                    <Button
                      variant="outline"
                      onClick={() => {
                        const groupChat = {
                          id: "group-suggested",
                          name: "Squad Gaming",
                          avatar: "🎮",
                          lastMessage: "Grupo creado - ¡A jugar!",
                          timestamp: "ahora",
                          isGroup: true,
                          participants: matches.map((m) => m.name),
                        };
                        setActiveChats([groupChat]);
                      }}
                    >
                      <Users className="w-4 h-4 mr-2" />
                      Crear
                    </Button>
                  </div>
                </CardContent>
              </Card>
            </div>
          )}
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
            <div className="flex items-center space-x-3">
              <div className="relative">
                <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                  <Gamepad2 className="w-6 h-6 text-primary-foreground" />
                </div>
                <div className="absolute -top-1 -right-1 w-4 h-4 bg-accent rounded-full animate-pulse"></div>
              </div>
              <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                SteamPals
              </h1>
            </div>
            <div className="flex items-center space-x-3">
              <Button
                variant="outline"
                onClick={() => setShowMatches(true)}
                className="relative border-primary/50 hover:bg-primary/10"
              >
                <UserCheck className="w-4 h-4 mr-2" />
                <span className="hidden sm:inline">Pals</span>
                {matches.length > 0 && (
                  <Badge className="absolute -top-2 -right-2 bg-accent text-xs">
                    {matches.length}
                  </Badge>
                )}
              </Button>

              <Button
                variant="ghost"
                className="hover:bg-muted/50"
                onClick={() => navigate("/chat")}
              >
                <MessageSquare className="w-4 h-4 mr-2" />
                <span className="hidden sm:inline">Chat</span>
              </Button>

              <Button
                variant="ghost"
                className="hover:bg-muted/50"
                onClick={() => navigate("/profile")}
              >
                <User className="w-4 h-4 mr-2" />
                <span className="hidden sm:inline">Mi Perfil</span>
              </Button>

              <Button
                variant="ghost"
                className="hover:bg-muted/50"
                onClick={() => navigate("/settings")}
              >
                <Settings className="w-4 h-4" />
                <span className="hidden sm:inline">Config</span>
              </Button>

              {/* Botón temporal para limpiar todo - solo para testing */}
              {(matches.length > 0 || activeChats.length > 0) && (
                <Button
                  variant="ghost"
                  className="hover:bg-destructive/20 text-destructive"
                  onClick={() => {
                    if (
                      confirm(
                        "¿Seguro que quieres limpiar todos los matches y chats?",
                      )
                    ) {
                      localStorage.removeItem("steamPalsMatches");
                      localStorage.removeItem("steamPalsChats");
                      setMatches([]);
                      setActiveChats([]);
                      setCurrentIndex(0);
                    }
                  }}
                  title="Limpiar todo"
                >
                  🗑️
                </Button>
              )}
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8 max-w-md">
        <div className="text-center mb-6">
          <h2 className="text-xl font-semibold mb-2">
            Encuentra tu squad perfecto
          </h2>
          <p className="text-muted-foreground">
            Desliza a la derecha si te gusta, izquierda si no
          </p>
        </div>

        {/* Card Stack */}
        <div className="relative h-[600px] mb-8">
          {profiles
            .slice(currentIndex, currentIndex + 2)
            .map((profile, index) => (
              <SwipeCard
                key={profile.id}
                profile={profile}
                index={index}
                onSwipe={index === 0 ? handleSwipe : undefined}
                navigate={navigate}
              />
            ))}
        </div>

        {/* Action Buttons */}
        <div className="flex justify-center space-x-6">
          <Button
            size="lg"
            variant="outline"
            className="w-16 h-16 rounded-full border-destructive/50 hover:bg-destructive/10"
            onClick={() => handleSwipe("left")}
          >
            <X className="w-8 h-8 text-destructive" />
          </Button>

          <Button
            size="lg"
            className="w-16 h-16 rounded-full bg-gradient-to-r from-accent to-primary hover:from-accent/90 hover:to-primary/90"
            onClick={() => handleSwipe("right")}
          >
            <Gamepad2 className="w-8 h-8" />
          </Button>
        </div>
      </main>
    </div>
  );
}

function SwipeCard({
  profile,
  index,
  onSwipe,
  navigate,
}: {
  profile: GamerProfile;
  index: number;
  onSwipe?: (direction: "left" | "right") => void;
  navigate: (path: string) => void;
}) {
  const x = useMotionValue(0);
  const rotate = useTransform(x, [-200, 200], [-30, 30]);
  const opacity = useTransform(x, [-200, -100, 0, 100, 200], [0, 1, 1, 1, 0]);

  function handleDragEnd(_: any, info: PanInfo) {
    if (!onSwipe) return;

    const offset = info.offset.x;
    const velocity = info.velocity.x;

    if (offset > 100 || velocity > 500) {
      onSwipe("right");
    } else if (offset < -100 || velocity < -500) {
      onSwipe("left");
    }
  }

  const cardVariants = {
    initial: { scale: index === 0 ? 1 : 0.95, y: index * 10 },
    animate: { scale: index === 0 ? 1 : 0.95, y: index * 10 },
  };

  return (
    <motion.div
      className="absolute inset-0"
      style={{ x, rotate, opacity, zIndex: 10 - index }}
      variants={cardVariants}
      initial="initial"
      animate="animate"
      drag={index === 0 ? "x" : false}
      dragConstraints={{ left: 0, right: 0 }}
      onDragEnd={handleDragEnd}
      whileDrag={{ scale: 1.05 }}
    >
      <Card className="h-full bg-card/90 backdrop-blur-sm border-border/50 shadow-2xl overflow-hidden">
        <CardContent className="p-0 h-full">
          {/* Profile Image Section */}
          <div className="h-2/3 bg-gradient-to-br from-primary/20 via-accent/20 to-primary/20 relative flex items-center justify-center">
            <div className="text-8xl">{profile.avatar}</div>


            {/* Level Badge */}
            <div className="absolute top-4 right-4 bg-accent/20 border border-accent/30 rounded-full px-3 py-1">
              <span className="text-accent font-bold text-sm">
                Lvl {profile.level}
              </span>
            </div>
          </div>

          {/* Profile Info */}
          <div className="h-1/3 p-4 space-y-3">
            <div>
              <h3 className="text-xl font-bold flex items-center gap-2">
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    navigate(`/user/${profile.name}`);
                  }}
                  className="text-primary hover:text-accent transition-colors cursor-pointer underline decoration-transparent hover:decoration-current"
                >
                  {profile.name}
                </button>
                <span className="text-muted-foreground font-normal">
                  {profile.age}
                </span>
              </h3>
              <div className="flex items-center text-sm text-muted-foreground">
                <MapPin className="w-4 h-4 mr-1" />
                {profile.location}
              </div>
            </div>

            <p className="text-sm text-muted-foreground line-clamp-2">
              {profile.bio}
            </p>

            <div className="flex flex-wrap gap-1">
              {profile.favoriteGames.slice(0, 3).map((game) => (
                <Badge key={game} variant="secondary" className="text-xs">
                  {game}
                </Badge>
              ))}
            </div>

            <div className="flex justify-between text-xs text-muted-foreground">
              <div className="flex items-center">
                <Clock className="w-3 h-3 mr-1" />
                {profile.hoursPlayed}
              </div>
              <div className="flex items-center">
                <Trophy className="w-3 h-3 mr-1" />
                {profile.achievements} logros
              </div>
            </div>
          </div>
        </CardContent>
      </Card>
    </motion.div>
  );
}

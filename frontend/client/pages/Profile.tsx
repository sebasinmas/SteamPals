import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useEffect } from "react";

import {
  Gamepad2,
  ArrowLeft,
  Edit3,
  Save,
  X,
  Monitor,
  Trophy,
  Clock,
  Star,
  MapPin,
  Calendar,
} from "lucide-react";

export default function Profile() {
  const [isEditing, setIsEditing] = useState(false);
  const [profile, setProfile] = useState<any>(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) throw new Error("No token found");

        const res = await fetch("http://localhost:8080/api/usuario/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) {
          throw new Error("No se pudo cargar el perfil");
        }

        const data = await res.json();
        setProfile({
          username: data.usuario,
          bio: data.descripcion || "Sin descripción",
          location: data.pais || "Ubicación desconocida",
          age: data.edad,
          favoriteGames: [],
          playStyle: [],
          steamConnected: true,
          level: 1,
          achievements: 0,
          hoursPlayed: "0h",
          memberSince: "2025",
        });
      } catch (err) {
        console.error("Error al cargar el perfil:", err);
      }
    };

    fetchProfile();
  }, []);

  const handleSave = () => {
    setIsEditing(false);
    console.log("Perfil guardado:", profile);
  };

  const handleCancel = () => {
    setIsEditing(false);
    // Aquí podrías revertir los cambios
  };
  if (!profile) {
  return (
    <div className="h-screen flex items-center justify-center">
      <p className="text-muted-foreground text-lg">Cargando perfil...</p>
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
            <div className="flex items-center space-x-3">
              <Link to="/home">
                <Button variant="ghost" className="hover:bg-muted/50">
                  <ArrowLeft className="w-4 h-4 mr-2" />
                  Volver
                </Button>
              </Link>
              {!isEditing ? (
                <Button
                  onClick={() => setIsEditing(true)}
                  className="bg-primary hover:bg-primary/90"
                >
                  <Edit3 className="w-4 h-4 mr-2" />
                  Editar
                </Button>
              ) : (
                <div className="flex space-x-2">
                  <Button
                    onClick={handleSave}
                    className="bg-green-600 hover:bg-green-700"
                  >
                    <Save className="w-4 h-4 mr-2" />
                    Guardar
                  </Button>
                  <Button onClick={handleCancel} variant="outline">
                    <X className="w-4 h-4 mr-2" />
                    Cancelar
                  </Button>
                </div>
              )}
            </div>
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
                    🎮
                  </div>
                </div>

                {isEditing ? (
                  <div className="space-y-3">
                    <Input
                      value={profile.username}
                      onChange={(e) =>
                        setProfile({ ...profile, username: e.target.value })
                      }
                      className="text-center font-bold text-lg"
                    />
                    <Input
                      value={profile.location}
                      onChange={(e) =>
                        setProfile({ ...profile, location: e.target.value })
                      }
                      className="text-center text-sm"
                    />
                  </div>
                ) : (
                  <div>
                    <h2 className="text-2xl font-bold mb-2">
                      {profile.username}
                    </h2>
                    <div className="flex items-center justify-center text-muted-foreground mb-2">
                      <MapPin className="w-4 h-4 mr-1" />
                      {profile.location}
                    </div>
                  </div>
                )}

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
                  {!profile.steamConnected && (
                    <Button className="w-full mt-3" variant="outline">
                      Conectar Steam
                    </Button>
                  )}
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
                {isEditing ? (
                  <Textarea
                    value={profile.bio}
                    onChange={(e) =>
                      setProfile({ ...profile, bio: e.target.value })
                    }
                    placeholder="Cuéntanos sobre ti y tu estilo de juego..."
                    className="min-h-[100px]"
                  />
                ) : (
                  <p className="text-muted-foreground">{profile.bio}</p>
                )}
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
                {isEditing ? (
                  <div className="space-y-2">
                    <Label>
                      Agrega tus juegos favoritos (separados por coma)
                    </Label>
                    <Input
                      value={profile.favoriteGames.join(", ")}
                      onChange={(e) =>
                        setProfile({
                          ...profile,
                          favoriteGames: e.target.value
                            .split(", ")
                            .filter((g) => g.trim()),
                        })
                      }
                      placeholder="CS2, Valorant, Elden Ring..."
                    />
                  </div>
                ) : (
                  <div className="flex flex-wrap gap-2">
                    {profile.favoriteGames.map((game) => (
                      <Badge key={game} variant="secondary" className="text-sm">
                        {game}
                      </Badge>
                    ))}
                  </div>
                )}
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}

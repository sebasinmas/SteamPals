import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  Gamepad2,
  ArrowLeft,
  Shield,
  Search,
  Ban,
  UserX,
  AlertTriangle,
  Clock,
  CheckCircle,
} from "lucide-react";

interface BannedUser {
  id: string;
  username: string;
  avatar: string;
  banDate: string;
  banDuration: string;
  reason: string;
  bannedBy: string;
  status: "active" | "expired";
}

export default function Admin() {
  const [searchUsername, setSearchUsername] = useState("");
  const [banReason, setBanReason] = useState("");
  const [banDuration, setBanDuration] = useState("");
  const [foundUser, setFoundUser] = useState<any>(null);
  const [bannedUsers, setBannedUsers] = useState<BannedUser[]>([
    {
      id: "1",
      username: "ToxicPlayer123",
      avatar: "😠",
      banDate: "2024-01-15",
      banDuration: "7 días",
      reason: "Comportamiento tóxico en chat",
      bannedBy: "Admin",
      status: "active",
    },
    {
      id: "2",
      username: "Cheater456",
      avatar: "🤖",
      banDate: "2024-01-10",
      banDuration: "30 días",
      reason: "Uso de cheats detectado",
      bannedBy: "Admin",
      status: "active",
    },
  ]);

  const searchUser = () => {
    if (!searchUsername.trim()) return;

    // Simular búsqueda de usuario
    const mockUser = {
      username: searchUsername,
      avatar: "🎮",
      level: 45,
      joinDate: "2023-12-01",
      status: "online",
    };

    setFoundUser(mockUser);
  };

  const banUser = () => {
    if (!foundUser || !banReason.trim() || !banDuration) {
      alert("Por favor completa todos los campos");
      return;
    }

    const newBan: BannedUser = {
      id: Date.now().toString(),
      username: foundUser.username,
      avatar: foundUser.avatar,
      banDate: new Date().toISOString().split("T")[0],
      banDuration: banDuration,
      reason: banReason,
      bannedBy: "Admin",
      status: "active",
    };

    setBannedUsers([newBan, ...bannedUsers]);
    setFoundUser(null);
    setSearchUsername("");
    setBanReason("");
    setBanDuration("");

    console.log(`Usuario ${foundUser.username} baneado por: ${banReason}`);
  };

  const unbanUser = (userId: string) => {
    setBannedUsers(
      bannedUsers.map((user) =>
        user.id === userId ? { ...user, status: "expired" } : user,
      ),
    );
  };

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
                SteamPals Admin
              </h1>
            </Link>
            <div className="flex items-center space-x-3">
              <Badge className="bg-red-600">
                <Shield className="w-3 h-3 mr-1" />
                Admin
              </Badge>
              <Link to="/home">
                <Button variant="ghost" className="hover:bg-muted/50">
                  <ArrowLeft className="w-4 h-4 mr-2" />
                  Volver
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8 max-w-6xl">
        <div className="space-y-8">
          <div>
            <h1 className="text-3xl font-bold mb-2">Panel de Administración</h1>
            <p className="text-muted-foreground">
              Gestiona usuarios y moderación de la plataforma
            </p>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            {/* Ban User */}
            <div className="space-y-6">
              <Card className="bg-card/80 backdrop-blur-sm border-border/50">
                <CardHeader>
                  <CardTitle className="flex items-center">
                    <UserX className="w-5 h-5 mr-2" />
                    Banear Usuario
                  </CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  {/* Search User */}
                  <div className="space-y-2">
                    <Label>Buscar usuario</Label>
                    <div className="flex space-x-2">
                      <Input
                        placeholder="Nombre de usuario..."
                        value={searchUsername}
                        onChange={(e) => setSearchUsername(e.target.value)}
                        onKeyPress={(e) => e.key === "Enter" && searchUser()}
                      />
                      <Button onClick={searchUser}>
                        <Search className="w-4 h-4" />
                      </Button>
                    </div>
                  </div>

                  {/* Found User */}
                  {foundUser && (
                    <Card className="bg-muted/20">
                      <CardContent className="p-4">
                        <div className="flex items-center space-x-3">
                          <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-xl">
                            {foundUser.avatar}
                          </div>
                          <div>
                            <h3 className="font-medium">
                              {foundUser.username}
                            </h3>
                            <p className="text-sm text-muted-foreground">
                              Nivel {foundUser.level} • Miembro desde{" "}
                              {foundUser.joinDate}
                            </p>
                            <Badge
                              variant={
                                foundUser.status === "online"
                                  ? "default"
                                  : "secondary"
                              }
                              className="text-xs"
                            >
                              {foundUser.status === "online"
                                ? "En línea"
                                : "Desconectado"}
                            </Badge>
                          </div>
                        </div>
                      </CardContent>
                    </Card>
                  )}

                  {/* Ban Details */}
                  {foundUser && (
                    <>
                      <div className="space-y-2">
                        <Label>Duración del ban</Label>
                        <Select
                          value={banDuration}
                          onValueChange={setBanDuration}
                        >
                          <SelectTrigger>
                            <SelectValue placeholder="Selecciona duración" />
                          </SelectTrigger>
                          <SelectContent>
                            <SelectItem value="1 hora">1 hora</SelectItem>
                            <SelectItem value="24 horas">24 horas</SelectItem>
                            <SelectItem value="3 días">3 días</SelectItem>
                            <SelectItem value="7 días">7 días</SelectItem>
                            <SelectItem value="30 días">30 días</SelectItem>
                            <SelectItem value="permanente">
                              Permanente
                            </SelectItem>
                          </SelectContent>
                        </Select>
                      </div>

                      <div className="space-y-2">
                        <Label>Razón del ban</Label>
                        <Textarea
                          placeholder="Describe el motivo del ban..."
                          value={banReason}
                          onChange={(e) => setBanReason(e.target.value)}
                        />
                      </div>

                      <Button
                        onClick={banUser}
                        variant="destructive"
                        className="w-full"
                      >
                        <Ban className="w-4 h-4 mr-2" />
                        Banear Usuario
                      </Button>
                    </>
                  )}
                </CardContent>
              </Card>
            </div>

            {/* Banned Users List */}
            <div>
              <Card className="bg-card/80 backdrop-blur-sm border-border/50">
                <CardHeader>
                  <CardTitle className="flex items-center">
                    <AlertTriangle className="w-5 h-5 mr-2" />
                    Usuarios Baneados
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4 max-h-96 overflow-y-auto">
                    {bannedUsers.map((user) => (
                      <Card
                        key={user.id}
                        className={`${
                          user.status === "active"
                            ? "border-red-500/50"
                            : "border-green-500/50"
                        }`}
                      >
                        <CardContent className="p-4">
                          <div className="flex items-center justify-between">
                            <div className="flex items-center space-x-3">
                              <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                                {user.avatar}
                              </div>
                              <div>
                                <h4 className="font-medium">{user.username}</h4>
                                <p className="text-xs text-muted-foreground">
                                  {user.reason}
                                </p>
                                <div className="flex items-center space-x-2 mt-1">
                                  <Badge
                                    variant={
                                      user.status === "active"
                                        ? "destructive"
                                        : "secondary"
                                    }
                                    className="text-xs"
                                  >
                                    {user.status === "active"
                                      ? "Activo"
                                      : "Expirado"}
                                  </Badge>
                                  <span className="text-xs text-muted-foreground">
                                    {user.banDuration}
                                  </span>
                                </div>
                              </div>
                            </div>

                            {user.status === "active" && (
                              <Button
                                size="sm"
                                variant="outline"
                                onClick={() => unbanUser(user.id)}
                              >
                                <CheckCircle className="w-3 h-3 mr-1" />
                                Quitar ban
                              </Button>
                            )}
                          </div>

                          <div className="mt-3 text-xs text-muted-foreground">
                            <div className="flex items-center space-x-4">
                              <span>📅 {user.banDate}</span>
                              <span>👮 {user.bannedBy}</span>
                            </div>
                          </div>
                        </CardContent>
                      </Card>
                    ))}

                    {bannedUsers.length === 0 && (
                      <div className="text-center py-8">
                        <CheckCircle className="w-12 h-12 text-green-500 mx-auto mb-3" />
                        <p className="text-muted-foreground">
                          No hay usuarios baneados
                        </p>
                      </div>
                    )}
                  </div>
                </CardContent>
              </Card>
            </div>
          </div>

          {/* Statistics */}
          <Card className="bg-card/80 backdrop-blur-sm border-border/50">
            <CardHeader>
              <CardTitle>Estadísticas de Moderación</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
                <div className="text-center p-4 bg-muted/20 rounded-lg">
                  <div className="text-2xl font-bold text-red-500">
                    {bannedUsers.filter((u) => u.status === "active").length}
                  </div>
                  <div className="text-sm text-muted-foreground">
                    Bans activos
                  </div>
                </div>
                <div className="text-center p-4 bg-muted/20 rounded-lg">
                  <div className="text-2xl font-bold text-green-500">
                    {bannedUsers.filter((u) => u.status === "expired").length}
                  </div>
                  <div className="text-sm text-muted-foreground">
                    Bans expirados
                  </div>
                </div>
                <div className="text-center p-4 bg-muted/20 rounded-lg">
                  <div className="text-2xl font-bold text-blue-500">
                    {bannedUsers.length}
                  </div>
                  <div className="text-sm text-muted-foreground">
                    Total bans
                  </div>
                </div>
                <div className="text-center p-4 bg-muted/20 rounded-lg">
                  <div className="text-2xl font-bold text-accent">0</div>
                  <div className="text-sm text-muted-foreground">
                    Reportes pendientes
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      </main>
    </div>
  );
}

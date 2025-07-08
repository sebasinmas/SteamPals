import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Gamepad2, ArrowLeft, Lock, Trash2, Monitor } from "lucide-react";

export default function Settings() {
  const [email, setEmail] = useState("shadowhunter87@email.com");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [steamConnected, setSteamConnected] = useState(true);

  const updatePassword = () => {
    if (newPassword !== confirmPassword) {
      alert("Las contraseñas no coinciden");
      return;
    }
    console.log("Actualizando contraseña...");
    setCurrentPassword("");
    setNewPassword("");
    setConfirmPassword("");
  };

  const connectSteam = () => {
    setSteamConnected(!steamConnected);
    console.log(
      steamConnected ? "Desconectando Steam..." : "Conectando Steam...",
    );
  };

  const deleteAccount = () => {
    if (
      confirm(
        "¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.",
      )
    ) {
      console.log("Eliminando cuenta...");
    }
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

      <main className="container mx-auto px-4 py-8 max-w-2xl">
        <div className="space-y-8">
          <div>
            <h1 className="text-3xl font-bold mb-2">Configuración</h1>
            <p className="text-muted-foreground">
              Gestiona tu cuenta y configuración
            </p>
          </div>

          <div className="space-y-6">
            {/* Email */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Lock className="w-5 h-5 mr-2" />
                  Email
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="email">Dirección de correo</Label>
                  <Input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <Button size="sm">Actualizar email</Button>
              </CardContent>
            </Card>

            {/* Password */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle>Cambiar contraseña</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="current-password">Contraseña actual</Label>
                  <Input
                    id="current-password"
                    type="password"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    placeholder="Tu contraseña actual"
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="new-password">Nueva contraseña</Label>
                  <Input
                    id="new-password"
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="Nueva contraseña"
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="confirm-password">
                    Confirmar nueva contraseña
                  </Label>
                  <Input
                    id="confirm-password"
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    placeholder="Confirmar nueva contraseña"
                  />
                </div>
                <Button onClick={updatePassword} size="sm">
                  Actualizar contraseña
                </Button>
              </CardContent>
            </Card>

            {/* Steam Connection */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Monitor className="w-5 h-5 mr-2" />
                  Conexión Steam
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex items-center justify-between p-4 bg-muted/20 rounded-lg">
                  <div className="flex items-center space-x-3">
                    <Monitor className="w-6 h-6 text-primary" />
                    <div>
                      <div className="font-medium">Steam</div>
                      <div className="text-sm text-muted-foreground">
                        {steamConnected ? "Conectado" : "Desconectado"}
                      </div>
                    </div>
                  </div>
                  <Button
                    variant={steamConnected ? "destructive" : "default"}
                    onClick={connectSteam}
                    size="sm"
                  >
                    {steamConnected ? "Desconectar" : "Conectar"}
                  </Button>
                </div>
                {!steamConnected && (
                  <p className="text-sm text-muted-foreground mt-3">
                    Conecta tu cuenta de Steam para sincronizar tus juegos y
                    estadísticas.
                  </p>
                )}
              </CardContent>
            </Card>

            {/* Delete Account */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50 border-destructive/50">
              <CardHeader>
                <CardTitle className="text-destructive">
                  Eliminar cuenta
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <p className="text-sm text-muted-foreground">
                  Esta acción eliminará permanentemente tu cuenta y todos tus
                  datos. No podrás recuperar tu información una vez eliminada.
                </p>
                <Button
                  variant="destructive"
                  onClick={deleteAccount}
                  className="w-full justify-start"
                >
                  <Trash2 className="w-4 h-4 mr-2" />
                  Eliminar mi cuenta
                </Button>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Gamepad2, Users, Trophy, Zap } from "lucide-react";

export default function Index() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Registration attempt:", { username, password });
  };

  const handleLogin = () => {
    navigate("/login");
  };

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
            <Button
              variant="outline"
              onClick={handleLogin}
              className="border-primary/50 hover:bg-primary/10"
            >
              Iniciar Sesión
            </Button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8 lg:py-16">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center min-h-[calc(100vh-200px)]">
          {/* Left Side - Content */}
          <div className="space-y-8">
            <div className="space-y-6">
              <div className="inline-flex items-center space-x-2 px-4 py-2 bg-accent/10 border border-accent/20 rounded-full text-accent font-medium">
                <Zap className="w-4 h-4" />
                <span>Nueva plataforma gaming</span>
              </div>

              <h2 className="text-4xl lg:text-6xl font-bold leading-tight">
                Regístrate y disfruta de{" "}
                <span className="bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                  una plataforma
                </span>{" "}
                donde puedes encontrar a{" "}
                <span className="bg-gradient-to-r from-accent to-primary bg-clip-text text-transparent">
                  nuevos amigos
                </span>{" "}
                en Steam!
              </h2>

              <p className="text-xl text-muted-foreground leading-relaxed max-w-lg">
                Conecta con jugadores de todo el mundo, forma equipos épicos y
                vive experiencias inolvidables en tus juegos favoritos.
              </p>
            </div>

            {/* Features */}
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 pt-8">
              <div className="flex items-center space-x-3 p-4 rounded-lg bg-card/50 border border-border/50">
                <Users className="w-8 h-8 text-primary" />
                <div>
                  <h3 className="font-semibold">Comunidad</h3>
                  <p className="text-sm text-muted-foreground">
                    Miles de gamers
                  </p>
                </div>
              </div>

              <div className="flex items-center space-x-3 p-4 rounded-lg bg-card/50 border border-border/50">
                <Trophy className="w-8 h-8 text-accent" />
                <div>
                  <h3 className="font-semibold">Torneos</h3>
                  <p className="text-sm text-muted-foreground">
                    Competiciones épicas
                  </p>
                </div>
              </div>

              <div className="flex items-center space-x-3 p-4 rounded-lg bg-card/50 border border-border/50">
                <Gamepad2 className="w-8 h-8 text-primary" />
                <div>
                  <h3 className="font-semibold">Juegos</h3>
                  <p className="text-sm text-muted-foreground">
                    Todos los géneros
                  </p>
                </div>
              </div>
            </div>

            {/* Logo Placeholder */}
            <div className="hidden lg:flex justify-center pt-8">
              <div className="w-32 h-32 rounded-full border-2 border-dashed border-primary/30 flex items-center justify-center bg-gradient-to-br from-primary/5 to-accent/5">
                <div className="w-16 h-16 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                  <Gamepad2 className="w-8 h-8 text-primary-foreground" />
                </div>
              </div>
            </div>
          </div>

          {/* Right Side - Registration Form */}
          <div className="flex justify-center lg:justify-end">
            <Card className="w-full max-w-md bg-card/80 backdrop-blur-sm border-border/50 shadow-2xl">
              <CardHeader className="text-center space-y-2">
                <CardTitle className="text-2xl font-bold">
                  ¿Todavía no estás registrado?
                </CardTitle>
                <CardDescription className="text-lg font-medium text-accent">
                  ¡Regístrate!
                </CardDescription>
              </CardHeader>

              <CardContent>
                <form onSubmit={handleRegister} className="space-y-6">
                  <div className="space-y-2">
                    <Label htmlFor="username" className="text-sm font-medium">
                      Nombre de Usuario
                    </Label>
                    <Input
                      id="username"
                      type="text"
                      placeholder="Tu nombre de usuario"
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                      className="h-12 bg-input/50 border-border/50 focus:border-primary transition-colors"
                      required
                    />
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="password" className="text-sm font-medium">
                      Contraseña
                    </Label>
                    <Input
                      id="password"
                      type="password"
                      placeholder="Tu contraseña segura"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="h-12 bg-input/50 border-border/50 focus:border-primary transition-colors"
                      required
                    />
                  </div>

                  <Button
                    type="submit"
                    className="w-full h-12 bg-gradient-to-r from-primary to-accent hover:from-primary/90 hover:to-accent/90 text-white font-semibold text-lg shadow-lg hover:shadow-xl transition-all duration-200"
                  >
                    Registrarse
                  </Button>
                </form>

                <div className="mt-6 text-center text-sm text-muted-foreground">
                  ¿Ya tienes cuenta?{" "}
                  <button
                    onClick={handleLogin}
                    className="text-primary hover:text-accent font-medium hover:underline transition-colors"
                  >
                    Inicia sesión aquí
                  </button>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>

      {/* Background Effects */}
      <div className="fixed inset-0 -z-10 overflow-hidden pointer-events-none">
        <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-primary/5 rounded-full blur-3xl animate-pulse"></div>
        <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-accent/5 rounded-full blur-3xl animate-pulse delay-1000"></div>
        <div className="absolute top-3/4 left-3/4 w-64 h-64 bg-primary/3 rounded-full blur-2xl animate-pulse delay-2000"></div>
      </div>
    </div>
  );
}

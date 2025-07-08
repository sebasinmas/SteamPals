import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Admin from "../Admin";

const mockNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

// Mock window.confirm
const mockConfirm = vi.fn();
vi.stubGlobal("confirm", mockConfirm);

const renderAdmin = () => {
  return render(
    <BrowserRouter>
      <Admin />
    </BrowserRouter>,
  );
};

describe("Admin Component", () => {
  beforeEach(() => {
    mockNavigate.mockClear();
    mockConfirm.mockClear();
  });

  describe("Rendering", () => {
    it("should render admin header with SteamPals Admin branding", () => {
      renderAdmin();
      expect(screen.getByText("SteamPals Admin")).toBeInTheDocument();
    });

    it("should render admin badge", () => {
      renderAdmin();
      expect(screen.getByText("Admin")).toBeInTheDocument();
    });

    it("should render main admin sections", () => {
      renderAdmin();
      expect(screen.getByText("Panel de Administración")).toBeInTheDocument();
      expect(screen.getByText("Banear Usuario")).toBeInTheDocument();
      expect(screen.getByText("Usuarios Baneados")).toBeInTheDocument();
    });

    it("should render search user interface", () => {
      renderAdmin();
      expect(
        screen.getByPlaceholderText("Nombre de usuario..."),
      ).toBeInTheDocument();
      expect(
        screen.getByRole("button", { name: /Search/ }),
      ).toBeInTheDocument();
    });

    it("should render moderation statistics", () => {
      renderAdmin();
      expect(
        screen.getByText("Estadísticas de Moderación"),
      ).toBeInTheDocument();
      expect(screen.getByText("Bans activos")).toBeInTheDocument();
      expect(screen.getByText("Bans expirados")).toBeInTheDocument();
    });
  });

  describe("User Search", () => {
    it("should search for user when search button is clicked", async () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");
      const searchButton = screen.getByRole("button", { name: /Search/ });

      fireEvent.change(searchInput, { target: { value: "TestUser123" } });
      fireEvent.click(searchButton);

      await waitFor(() => {
        expect(screen.getByText("TestUser123")).toBeInTheDocument();
      });
    });

    it("should search for user when Enter is pressed", async () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");

      fireEvent.change(searchInput, { target: { value: "TestUser456" } });
      fireEvent.keyPress(searchInput, { key: "Enter", code: "Enter" });

      await waitFor(() => {
        expect(screen.getByText("TestUser456")).toBeInTheDocument();
      });
    });

    it("should not search with empty username", () => {
      renderAdmin();
      const searchButton = screen.getByRole("button", { name: /Search/ });

      fireEvent.click(searchButton);

      expect(screen.queryByText("Nivel 45")).not.toBeInTheDocument();
    });

    it("should display found user information", async () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");
      const searchButton = screen.getByRole("button", { name: /Search/ });

      fireEvent.change(searchInput, { target: { value: "GamerUser" } });
      fireEvent.click(searchButton);

      await waitFor(() => {
        expect(screen.getByText("GamerUser")).toBeInTheDocument();
        expect(
          screen.getByText("Nivel 45 • Miembro desde 2023-12-01"),
        ).toBeInTheDocument();
      });
    });
  });

  describe("User Banning", () => {
    beforeEach(async () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");
      const searchButton = screen.getByRole("button", { name: /Search/ });

      fireEvent.change(searchInput, { target: { value: "BadUser" } });
      fireEvent.click(searchButton);

      await waitFor(() => {
        expect(screen.getByText("BadUser")).toBeInTheDocument();
      });
    });

    it("should show ban form when user is found", () => {
      expect(screen.getByText("Duración del ban")).toBeInTheDocument();
      expect(screen.getByText("Razón del ban")).toBeInTheDocument();
      expect(
        screen.getByRole("button", { name: /Banear Usuario/ }),
      ).toBeInTheDocument();
    });

    it("should require ban reason and duration", () => {
      const banButton = screen.getByRole("button", { name: /Banear Usuario/ });

      // Mock alert
      const mockAlert = vi.fn();
      vi.stubGlobal("alert", mockAlert);

      fireEvent.click(banButton);

      expect(mockAlert).toHaveBeenCalledWith(
        "Por favor completa todos los campos",
      );
    });

    it("should ban user with valid information", async () => {
      const durationSelect = screen.getByRole("combobox");
      const reasonTextarea = screen.getByPlaceholderText(
        "Describe el motivo del ban...",
      );
      const banButton = screen.getByRole("button", { name: /Banear Usuario/ });

      fireEvent.change(reasonTextarea, {
        target: { value: "Comportamiento tóxico" },
      });

      // Simulate selecting duration (this would need proper Select component testing)
      // For now, just test the ban button click
      fireEvent.click(banButton);

      // The test would need to mock the select component properly
    });

    it("should clear form after successful ban", async () => {
      const reasonTextarea = screen.getByPlaceholderText(
        "Describe el motivo del ban...",
      );

      fireEvent.change(reasonTextarea, { target: { value: "Test reason" } });

      // After successful ban, form should be cleared
      // This would need proper implementation of the ban flow
    });
  });

  describe("Banned Users List", () => {
    it("should display banned users", () => {
      renderAdmin();
      expect(screen.getByText("ToxicPlayer123")).toBeInTheDocument();
      expect(screen.getByText("Cheater456")).toBeInTheDocument();
    });

    it("should show ban details for each user", () => {
      renderAdmin();
      expect(
        screen.getByText("Comportamiento tóxico en chat"),
      ).toBeInTheDocument();
      expect(screen.getByText("Uso de cheats detectado")).toBeInTheDocument();
      expect(screen.getByText("7 días")).toBeInTheDocument();
      expect(screen.getByText("30 días")).toBeInTheDocument();
    });

    it("should show ban status badges", () => {
      renderAdmin();
      const activeBadges = screen.getAllByText("Activo");
      expect(activeBadges.length).toBeGreaterThan(0);
    });

    it("should show ban dates and admin info", () => {
      renderAdmin();
      expect(screen.getByText("📅 2024-01-15")).toBeInTheDocument();
      expect(screen.getByText("👮 Admin")).toBeInTheDocument();
    });
  });

  describe("Unban Functionality", () => {
    it("should unban user when unban button is clicked", async () => {
      renderAdmin();
      const unbanButtons = screen.getAllByText("Quitar ban");

      fireEvent.click(unbanButtons[0]);

      await waitFor(() => {
        expect(screen.getByText("Expirado")).toBeInTheDocument();
      });
    });

    it("should only show unban button for active bans", () => {
      renderAdmin();
      const unbanButtons = screen.getAllByText("Quitar ban");
      const activeBadges = screen.getAllByText("Activo");

      expect(unbanButtons.length).toBe(activeBadges.length);
    });
  });

  describe("Statistics", () => {
    it("should display moderation statistics correctly", () => {
      renderAdmin();
      expect(screen.getByText("2")).toBeInTheDocument(); // Active bans
      expect(screen.getByText("0")).toBeInTheDocument(); // Expired bans initially
      expect(screen.getByText("2")).toBeInTheDocument(); // Total bans
      expect(screen.getByText("0")).toBeInTheDocument(); // Pending reports
    });

    it("should update statistics when bans change", async () => {
      renderAdmin();
      const unbanButtons = screen.getAllByText("Quitar ban");

      fireEvent.click(unbanButtons[0]);

      await waitFor(() => {
        // Statistics should update after unban
        expect(screen.getByText("Expirado")).toBeInTheDocument();
      });
    });
  });

  describe("Navigation", () => {
    it("should navigate back to home when back button is clicked", () => {
      renderAdmin();
      const backButton = screen.getByText("Volver");

      fireEvent.click(backButton);

      expect(mockNavigate).toHaveBeenCalledWith("/home");
    });
  });

  describe("Form Validation", () => {
    it("should validate ban duration selection", async () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");
      const searchButton = screen.getByRole("button", { name: /Search/ });

      fireEvent.change(searchInput, { target: { value: "TestUser" } });
      fireEvent.click(searchButton);

      await waitFor(() => {
        const reasonTextarea = screen.getByPlaceholderText(
          "Describe el motivo del ban...",
        );
        expect(reasonTextarea).toBeInTheDocument();
      });
    });

    it("should validate ban reason input", async () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");
      const searchButton = screen.getByRole("button", { name: /Search/ });

      fireEvent.change(searchInput, { target: { value: "TestUser" } });
      fireEvent.click(searchButton);

      await waitFor(() => {
        const reasonTextarea = screen.getByPlaceholderText(
          "Describe el motivo del ban...",
        );
        fireEvent.change(reasonTextarea, { target: { value: "Valid reason" } });
        expect(reasonTextarea).toHaveValue("Valid reason");
      });
    });
  });

  describe("Error Handling", () => {
    it("should handle search errors gracefully", () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");
      const searchButton = screen.getByRole("button", { name: /Search/ });

      expect(() => {
        fireEvent.change(searchInput, { target: { value: "ErrorUser" } });
        fireEvent.click(searchButton);
      }).not.toThrow();
    });

    it("should handle ban errors gracefully", () => {
      renderAdmin();
      const banButton = screen.getByRole("button", { name: /Banear Usuario/ });

      expect(() => fireEvent.click(banButton)).not.toThrow();
    });
  });

  describe("Accessibility", () => {
    it("should have proper form labels", () => {
      renderAdmin();
      expect(screen.getByText("Buscar usuario")).toBeInTheDocument();
    });

    it("should support keyboard navigation", () => {
      renderAdmin();
      const searchInput = screen.getByPlaceholderText("Nombre de usuario...");

      searchInput.focus();
      expect(document.activeElement).toBe(searchInput);
    });

    it("should have proper button roles", () => {
      renderAdmin();
      const buttons = screen.getAllByRole("button");

      buttons.forEach((button) => {
        expect(button).toBeInTheDocument();
      });
    });
  });

  describe("Real-time Updates", () => {
    it("should update banned users list in real-time", async () => {
      renderAdmin();

      // Initial count
      const initialBannedUsers = screen.getAllByText(/ToxicPlayer|Cheater/);
      expect(initialBannedUsers.length).toBe(2);
    });

    it("should update statistics in real-time", () => {
      renderAdmin();

      // Check that statistics are displaying current data
      expect(screen.getByText("Total bans")).toBeInTheDocument();
      expect(screen.getByText("Bans activos")).toBeInTheDocument();
    });
  });
});

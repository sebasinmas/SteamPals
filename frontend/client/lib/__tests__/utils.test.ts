import { describe, it, expect } from "vitest";
import { cn } from "../utils";

describe("Utils", () => {
  describe("cn function", () => {
    it("should combine class names correctly", () => {
      const result = cn("class1", "class2");
      expect(result).toBe("class1 class2");
    });

    it("should handle conditional classes", () => {
      const condition = true;
      const result = cn("base-class", { "conditional-class": condition });
      expect(result).toBe("base-class conditional-class");
    });

    it("should exclude falsy conditional classes", () => {
      const condition = false;
      const result = cn("base-class", { "conditional-class": condition });
      expect(result).toBe("base-class");
    });

    it("should merge conflicting Tailwind classes", () => {
      const result = cn("px-2", "px-4");
      expect(result).toBe("px-4"); // Should keep the last one
    });

    it("should handle arrays of classes", () => {
      const result = cn(["class1", "class2"], "class3");
      expect(result).toBe("class1 class2 class3");
    });

    it("should handle undefined and null values", () => {
      const result = cn("class1", undefined, null, "class2");
      expect(result).toBe("class1 class2");
    });

    it("should handle empty strings", () => {
      const result = cn("class1", "", "class2");
      expect(result).toBe("class1 class2");
    });

    it("should handle complex conditional objects", () => {
      const isActive = true;
      const isDisabled = false;
      const result = cn(
        "base-class",
        {
          "active-class": isActive,
          "disabled-class": isDisabled,
        },
        "final-class",
      );
      expect(result).toBe("base-class active-class final-class");
    });

    it("should handle Tailwind modifier classes", () => {
      const result = cn("hover:bg-blue-500", "focus:bg-blue-600");
      expect(result).toBe("hover:bg-blue-500 focus:bg-blue-600");
    });

    it("should handle responsive classes", () => {
      const result = cn("text-sm", "md:text-base", "lg:text-lg");
      expect(result).toBe("text-sm md:text-base lg:text-lg");
    });

    it("should prioritize later conflicting classes", () => {
      const result = cn("bg-red-500", "bg-blue-500", "bg-green-500");
      expect(result).toBe("bg-green-500");
    });

    it("should handle dark mode classes", () => {
      const result = cn("bg-white", "dark:bg-black");
      expect(result).toBe("bg-white dark:bg-black");
    });

    it("should handle state classes properly", () => {
      const result = cn(
        "text-gray-700",
        "hover:text-blue-600",
        "focus:text-blue-800",
        "active:text-blue-900",
      );
      expect(result).toBe(
        "text-gray-700 hover:text-blue-600 focus:text-blue-800 active:text-blue-900",
      );
    });

    it("should handle spacing conflicts correctly", () => {
      const result = cn("p-2", "px-4", "py-3");
      expect(result).toBe("px-4 py-3");
    });

    it("should handle flex utilities", () => {
      const result = cn("flex", "items-center", "justify-between");
      expect(result).toBe("flex items-center justify-between");
    });

    it("should handle grid utilities", () => {
      const result = cn(
        "grid",
        "grid-cols-1",
        "md:grid-cols-2",
        "lg:grid-cols-3",
      );
      expect(result).toBe("grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3");
    });

    it("should handle animation classes", () => {
      const result = cn("animate-pulse", "transition-all", "duration-200");
      expect(result).toBe("animate-pulse transition-all duration-200");
    });

    it("should handle border utilities", () => {
      const result = cn("border", "border-gray-200", "rounded-lg");
      expect(result).toBe("border border-gray-200 rounded-lg");
    });

    it("should handle shadow utilities", () => {
      const result = cn("shadow-sm", "hover:shadow-md", "active:shadow-lg");
      expect(result).toBe("shadow-sm hover:shadow-md active:shadow-lg");
    });

    it("should handle typography utilities", () => {
      const result = cn("text-base", "font-medium", "leading-relaxed");
      expect(result).toBe("text-base font-medium leading-relaxed");
    });

    it("should handle position utilities", () => {
      const result = cn("absolute", "top-0", "right-0", "z-10");
      expect(result).toBe("absolute top-0 right-0 z-10");
    });

    it("should handle width and height utilities", () => {
      const result = cn("w-full", "h-screen", "max-w-md", "min-h-0");
      expect(result).toBe("w-full h-screen max-w-md min-h-0");
    });

    it("should handle gaming-specific color combinations", () => {
      const result = cn(
        "bg-gradient-to-r",
        "from-primary",
        "to-accent",
        "text-primary-foreground",
      );
      expect(result).toBe(
        "bg-gradient-to-r from-primary to-accent text-primary-foreground",
      );
    });

    it("should handle button variant combinations", () => {
      const result = cn(
        "inline-flex",
        "items-center",
        "justify-center",
        "rounded-md",
        "text-sm",
        "font-medium",
        "transition-colors",
      );
      expect(result).toBe(
        "inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors",
      );
    });

    it("should handle card styling combinations", () => {
      const result = cn(
        "bg-card",
        "backdrop-blur-sm",
        "border-border/50",
        "rounded-lg",
        "p-6",
        "shadow-lg",
      );
      expect(result).toBe(
        "bg-card backdrop-blur-sm border-border/50 rounded-lg p-6 shadow-lg",
      );
    });
  });

  describe("Edge Cases", () => {
    it("should handle no arguments", () => {
      const result = cn();
      expect(result).toBe("");
    });

    it("should handle only falsy values", () => {
      const result = cn(false, null, undefined, "");
      expect(result).toBe("");
    });

    it("should handle nested arrays", () => {
      const result = cn(["class1", ["class2", "class3"]], "class4");
      expect(result).toBe("class1 class2 class3 class4");
    });

    it("should handle mixed data types", () => {
      const result = cn(
        "base",
        true && "conditional",
        false && "hidden",
        { active: true, disabled: false },
        ["array1", "array2"],
      );
      expect(result).toBe("base conditional active array1 array2");
    });

    it("should handle very long class strings", () => {
      const longClass = "a".repeat(1000);
      const result = cn(longClass, "short");
      expect(result).toBe(`${longClass} short`);
    });

    it("should handle special characters in class names", () => {
      const result = cn('before:content-[""]', 'after:content-["•"]');
      expect(result).toBe('before:content-[""] after:content-["•"]');
    });

    it("should handle numeric class names", () => {
      const result = cn(
        "top-1/2",
        "left-1/2",
        "translate-x-1/2",
        "translate-y-1/2",
      );
      expect(result).toBe("top-1/2 left-1/2 translate-x-1/2 translate-y-1/2");
    });

    it("should handle arbitrary value classes", () => {
      const result = cn("bg-[#1f2937]", "text-[14px]", "w-[calc(100%-2rem)]");
      expect(result).toBe("bg-[#1f2937] text-[14px] w-[calc(100%-2rem)]");
    });
  });

  describe("Performance", () => {
    it("should handle many arguments efficiently", () => {
      const manyClasses = Array.from({ length: 100 }, (_, i) => `class-${i}`);
      const result = cn(...manyClasses);
      expect(result).toContain("class-0");
      expect(result).toContain("class-99");
    });

    it("should handle repeated calls efficiently", () => {
      const startTime = performance.now();

      for (let i = 0; i < 1000; i++) {
        cn("base-class", { active: i % 2 === 0 }, `dynamic-${i}`);
      }

      const endTime = performance.now();
      const duration = endTime - startTime;

      // Should complete in reasonable time (less than 100ms for 1000 calls)
      expect(duration).toBeLessThan(100);
    });
  });
});

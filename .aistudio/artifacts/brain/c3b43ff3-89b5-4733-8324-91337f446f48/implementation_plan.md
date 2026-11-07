# O7A — EV Spare Parts & Service Platform

A modern, high-performance native Android application for electric scooter spare-parts discovery, vehicle garage management, service center bookings, and operational admin management, designed faithfully to the **O7A** visual design and functional blueprints.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The three specification documents (`O7A.pdf`, `EV_Spare_Parts_Developer_Working_Roadmap.pdf`, and `EV_Business_Platform_Blueprint.pdf`) describe a cohesive platform connecting EV riders, service centers, and spare parts inventory. The key implementation decisions are summarized below:

- **UI & Layout Alignment with O7A Visuals**: Faithful recreation of the 4 iconic O7A screens:
  1. **Main Hub**: Electric scooter hero showcase with entry portals for `Customer`, `Spare Parts`, and `Service Center`.
  2. **Customer Hub**: Centralized QR/Barcode scanner with 8 grid action tiles (`Registration`, `Vehicle Info`, `Monitoring`, `Video Assistants`, `Demo Video`, `Call + Visit`, `Transfer to Higher Department`).
  3. **Service Center Hub**: Service booking (`Book + Appointment`), diagnostic services (`Software Section Service`), workshop demo videos, and direct spare parts link.
  4. **Service Spare Parts Hub**: Split entry into `Online Purchase Options` (e-commerce catalog, model compatibility filter, parts cart, UPI checkout) and `Offline Purchase Options` (service-center counter stock inspection and direct pickup reservation).
- **Architecture & Tech Stack**: Native Android app written in Kotlin and Jetpack Compose, adhering to Material 3 with custom O7A dark-navy/electric-blue tokens (`#15243B` dark navy, `#24558D` electric blue, `#00E5FF` cyan accent, `#16A36A` success).
- **Local Persistence & Offline Capability**: Powered by Android Room Database with reactive Kotlin Flows, pre-populated with popular EV models (Ola S1/Pro, Ather 450X, TVS iQube, Bajaj Chetak), OEM spare parts catalogue across major categories, and persistent orders/appointments.
- **Admin & Role Switching**: Seamless role switcher in the top bar to easily toggle between **Customer**, **Service Center Staff**, and **Admin / Inventory Manager** modes without complex auth barriers during evaluation.

---

## 1. Overview & Core Concept

### What It Does
O7A is an all-in-one EV scooter companion and commerce application that allows EV owners to:
- Identify parts and vehicles quickly via a built-in barcode/QR code scanner.
- Manage their personal EV scooter garage (models, battery capacity, warranty, odometer, and service logs).
- Browse and buy compatible spare parts (brake pads, belt drives, instrument clusters, chargers, suspension, switches) with strict vehicle compatibility filtering.
- Complete checkout using UPI QR code payment and UTR reference submission.
- Book service center appointments and inspect diagnostics/firmware guidelines.
- Escalate service issues to higher departments or initiate quick Call & Visit navigation.
- Switch to Admin / Service Center mode to manage stock quantities, verify payments, and update order statuses.

### Target Audience & Persona
- **EV Scooter Owners**: Looking for authentic, compatible spare parts and rapid service center booking without guesswork.
- **Service Center Technicians & Managers**: Monitoring workshop appointments, parts stock levels, and walk-in counter sales.
- **Parts Administrators**: Updating inventory, verifying customer payments, and fulfilling online orders.

---

## 2. User Experience & Visual Design

### Visual Identity & Theme
- **Aesthetic Direction**: High-tech automotive, dark-mode focused, futuristic electric mobility aesthetic inspired by the O7A reference boards.
- **Color Palette**:
  - `Background Dark Navy`: `#0B132B` / `#15243B`
  - `Surface Glassmorphic Card`: `#1C2B4A` with subtle stroke `#2A3F6D`
  - `Primary Action Blue`: `#24558D`
  - `Electric Cyan Highlight`: `#00D2FF`
  - `Verified Success Green`: `#16A36A`
  - `Pending / Attention Amber`: `#D97706`
  - `Text High-Contrast`: `#FFFFFF` and `#94A3B8`
- **Typography & Layout**: Bold condensed headers (`O7A` display brand), clear technical subtitles, and generous 48dp+ touch targets on all interactive cards and tiles.

```
┌─────────────────────────────────────────────────────────────┐
│                       O7A HERO HUB                          │
│                                                             │
│                [ Electric Scooter Visual ]                  │
│                                                             │
│  ┌──────────────┐   ┌──────────────┐   ┌─────────────────┐  │
│  │   Customer   │   │ Spare Parts  │   │ Service Center  │  │
│  │      ↗       │   │      ↗       │   │       ↗         │  │
│  └──────────────┘   └──────────────┘   └─────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Key Navigation Flows
1. **Root Main Hub (`MainHubScreen`)**:
   - Hero presentation with dark gradient atmosphere.
   - 3 primary portals: `Customer`, `Spare Parts`, and `Service Center`.
   - Top action bar with Role Switcher (Customer / Service Center / Admin) and Garage indicator.
2. **Customer Hub (`CustomerHubScreen`)**:
   - Prominent central **QR/Barcode Scanner** button for scanning part labels and vehicle codes.
   - Grid cards matching O7A Page 2:
     - `Scanner`: Camera/simulated scanner resolving part SKU and vehicle models.
     - `Registration`: Customer profile and personal details.
     - `Vehicle Related Information`: Garage view showing connected scooter details, warranty, battery health.
     - `Monitoring Options`: Scooter operational status, service interval calculator, and maintenance checklist.
     - `Video Assistants`: Step-by-step DIY installation and troubleshooting video guides.
     - `Demo Video`: Feature tour and scooter walkthrough.
     - `Call + Visit`: Quick-dial service center, GPS address, working hours, and location visit guide.
     - `Transfer to Higher Department`: Support ticket creation and escalation flow.
3. **Service Spare Parts Hub (`SparePartsHubScreen`)**:
   - Matching O7A Page 4 with two distinct options:
     - **Online Purchase Options**:
       - Category tabs: *Brakes*, *Electrical & Lights*, *Powertrain & Belt*, *Body & Chassis*, *Charging & Battery*, *Accessories*.
       - Vehicle filter dropdown: *Ola S1 / S1 Pro*, *Ather 450X*, *TVS iQube*, *Bajaj Chetak*, *All Models*.
       - Live stock indicators (*In Stock*, *Low Stock: X left*, *Out of Stock*).
       - Item detail bottom sheet / screen with part number/SKU, description, price, compatibility badges.
       - Shopping Cart with quantity adjustment and stock validation.
       - Checkout with delivery details, merchant UPI QR code display, UTR entry, and order generation.
     - **Offline Purchase Options**:
       - Service center counter parts check.
       - Reserve-for-pickup at nearest authorized service hub.
       - Counter quotation generator.
4. **Service Center Hub (`ServiceCenterHubScreen`)**:
   - Matching O7A Page 3:
     - `Book + Appointment`: Select vehicle, service type (General Periodic, Brake Overhaul, Software Update, Battery Diagnostic), date & time slot.
     - `Software Section Service`: ECU diagnostic check, firmware version info, and recalibration request.
     - `Video Assistants` & `Demo Video`: Workshop training and repair manuals.
     - `Spare Parts`: Direct link to workshop inventory.
5. **Admin & Workshop Dashboard (`AdminDashboardScreen`)**:
   - Overview metrics: Active Orders, Low Stock Alerts, Pending Appointments, Payment Verification Queue.
   - Inventory Management: Add new part, update stock count, adjust price, toggle availability.
   - Order Management: View customer orders, review submitted UTR payment references, verify payment (`Unpaid` → `Under Review` → `Verified`), update shipping status (`Submitted` → `Confirmed` → `Processing` → `Shipped` → `Delivered`).
   - Appointment Management: Confirm/reschedule customer service bookings.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Native Jetpack Compose Architecture vs Flutter**:
  - *Chosen Approach*: Implement cleanly in Kotlin Jetpack Compose within the current Android studio environment.
  - *Why*: Delivers 100% native performance, edge-to-edge system insets handling, fluid Material 3 animations, and instant compatibility with local Room database and Android camera capabilities.
- **Decision 2: Room Persistence with Initial Seed Data**:
  - *Chosen Approach*: Android Room database with Room DatabaseCallback to seed realistic EV scooter parts, vehicle models, categories, and sample service centers.
  - *Why*: Ensures the application is immediately testable and fully functional out-of-the-box offline, with persistent state across app restarts for carts, custom added vehicles, orders, and inventory modifications.
- **Decision 3: Manual UPI Payment MVP**:
  - *Chosen Approach*: Checkout displays a simulated dynamic Merchant UPI QR Code (and VPA `evspares.o7a@upi`), asks the user to enter their 12-digit UPI Transaction Reference (UTR), and transitions order to `Submitted / Under Review`. In Admin mode, the administrator can verify or reject the payment.
  - *Why*: Strictly aligns with Section 5 of the Developer Working Roadmap for the MVP release without requiring external merchant payment gateway credentials.

---

## 4. Technical Architecture & Data Strategy

### System Architecture Diagram

```
┌────────────────────────────────────────────────────────────────────────┐
│                          Jetpack Compose UI Layer                      │
│                                                                        │
│   ┌────────────────────┐   ┌────────────────────┐   ┌───────────────┐  │
│   │   MainHubScreen    │   │  CustomerHubScreen │   │ SparePartsHub │  │
│   └─────────┬──────────┘   └─────────┬──────────┘   └───────┬───────┘  │
│             │                        │                      │          │
│   ┌─────────┴──────────┐   ┌─────────┴──────────┐   ┌───────┴───────┐  │
│   │ ServiceCenterHub   │   │  CartCheckoutView  │   │ AdminDashboard│  │
│   └─────────┬──────────┘   └─────────┬──────────┘   └───────┬───────┘  │
│             └────────────────────────┼──────────────────────┘          │
│                                      ▼                                 │
│                      EVPlatformViewModel (StateFlow)                   │
└──────────────────────────────────────┬─────────────────────────────────┘
                                       │
                                       ▼
                         EVPlatformRepository
                                       │
            ┌──────────────────────────┴──────────────────────────┐
            ▼                                                     ▼
   Room Database (SQLite)                                SharedPreferences
   - ProductDao (Parts & stock)                          - Active Role (Customer / Admin)
   - VehicleDao (User EVs)                               - Selected Vehicle ID
   - OrderDao (Orders & items)
   - AppointmentDao (Bookings)
```

### Data Entities & Schema

1. **`ProductEntity`**:
   - `id`: Long (PK)
   - `sku`: String (e.g., `O7A-BRK-450X`)
   - `name`: String
   - `category`: String (`Brakes`, `Electrical`, `Powertrain`, `Chassis`, `Battery`, `Accessories`)
   - `price`: Double
   - `stockQty`: Int
   - `isAvailable`: Boolean
   - `description`: String
   - `compatibleModels`: String (Comma-separated, e.g., `Ola S1,Ola S1 Pro,Ather 450X`)
   - `barcode`: String (for scanner matching)
   - `iconName`: String

2. **`VehicleEntity`**:
   - `id`: Long (PK)
   - `make`: String (e.g., `Ola Electric`, `Ather Energy`, `TVS`)
   - `model`: String (e.g., `S1 Pro Gen 2`, `450X Apex`, `iQube ST`)
   - `regNumber`: String
   - `vin`: String
   - `batteryCapacity`: String (e.g., `4.0 kWh`)
   - `warrantyStatus`: String
   - `odoKm`: Int

3. **`OrderEntity` & `OrderItemEntity`**:
   - `orderId`: String (e.g., `O7A-ORD-8921`)
   - `customerName`: String
   - `customerPhone`: String
   - `deliveryAddress`: String
   - `totalAmount`: Double
   - `orderStatus`: String (`Pending`, `Confirmed`, `Processing`, `Shipped`, `Delivered`)
   - `paymentStatus`: String (`Unpaid`, `Under Review`, `Verified`)
   - `utrNumber`: String
   - `createdAt`: Long

4. **`AppointmentEntity`**:
   - `id`: Long (PK)
   - `vehicleInfo`: String
   - `serviceType`: String
   - `centerName`: String
   - `date`: String
   - `timeSlot`: String
   - `status`: String (`Booked`, `In Progress`, `Completed`, `Cancelled`)
   - `notes`: String

---

## 5. Implementation Steps & Verification Plan

1. **App Identity & Core Setup**:
   - Update `metadata.json` and `strings.xml` to **O7A EV Platform**.
   - Set up unique `applicationId` in `app/build.gradle.kts`.
   - Configure modern O7A dark navy / cyan automotive theme in `Theme.kt` and `Color.kt`.
2. **Room Database & Repository Layer**:
   - Create entities, DAOs, Database, and pre-populated seed data covering realistic EV models and spare parts catalog.
   - Build `EVPlatformRepository` with reactive flows.
3. **ViewModel & State Management**:
   - Implement `EVPlatformViewModel` with navigation backstack, active vehicle selection, search/filter criteria, cart management, checkout state, scanner resolution, and admin management actions.
4. **UI Screen Suite (Faithful to O7A)**:
   - `MainHubScreen`: Hero vehicle branding, O7A logo, 3 portal cards.
   - `CustomerHubScreen`: Scanner hero button, 8 grid action cards.
   - `ScannerDialog / Screen`: Interactive barcode & QR code scanner with simulated presets or camera.
   - `VehicleGarageScreen`: Add & inspect vehicles, battery stats, warranty info.
   - `SparePartsHubScreen`: Online catalog with category tabs, scooter compatibility chips, search, product modal, cart drawer, and UPI QR checkout.
   - `OfflinePurchaseScreen`: Counter inventory check and store reservation.
   - `ServiceCenterHubScreen`: Service booking modal, software diagnostics checklist, video tutorials.
   - `AdminDashboardScreen`: Inventory stock updates, order & payment verification (UTR checking), appointment scheduler.
5. **Compilation & Build Verification**:
   - Compile using `compile_applet` and verify clean build with zero errors.
